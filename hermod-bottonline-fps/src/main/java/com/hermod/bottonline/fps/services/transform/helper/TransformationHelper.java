package com.hermod.bottonline.fps.services.transform.helper;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.hermod.bottonline.fps.services.transform.helper.builder.BuilderRuleIf;
import com.hermod.bottonline.fps.services.transform.helper.builder.ComplexObjectBuilderRule;
import com.hermod.bottonline.fps.services.transform.helper.builder.ConversionBuilderRule;
import com.hermod.bottonline.fps.services.transform.helper.builder.RootBuilder;
import com.hermod.bottonline.fps.services.transform.helper.converter.ComplexTypeCollectionConverter;
import com.hermod.bottonline.fps.services.transform.helper.converter.ConvertFunction;
import com.hermod.bottonline.fps.services.transform.helper.converter.ConverterEntryIf;
import com.hermod.bottonline.fps.services.transform.helper.converter.EnumToStringConverter;
import com.hermod.bottonline.fps.services.transform.helper.converter.MatchingTypeConverter;
import com.hermod.bottonline.fps.services.transform.helper.converter.SimpleTypeCollectionConverter;
import com.hermod.bottonline.fps.services.transform.helper.converter.TypeConverterEntry;

import io.reactivex.Flowable;

public final class TransformationHelper {
	private static final Collection<ConverterEntryIf> Converters = new ArrayList<ConverterEntryIf>();
	private static HashMap<String, BuilderRuleIf> BuilderRules = new HashMap<String, BuilderRuleIf>();

	static {
		Converters.add(new TypeConverterEntry(XMLGregorianCalendar.class, Long.class, calendar -> (Long)((XMLGregorianCalendar)calendar).toGregorianCalendar().getTimeInMillis()));
		Converters.add(new TypeConverterEntry(XMLGregorianCalendar.class, CharSequence.class, calendar -> ((XMLGregorianCalendar)calendar).toXMLFormat()));
		Converters.add(new TypeConverterEntry(BigDecimal.class, Double.class, decimalValue -> ((BigDecimal)decimalValue).doubleValue()));
		Converters.add(new TypeConverterEntry(CharSequence.class,  XMLGregorianCalendar.class, TransformationHelper::charSequenceToXmlCalendar)); 
		Converters.add(new EnumToStringConverter());
		Converters.add(new MatchingTypeConverter());
		Converters.add(new ComplexTypeCollectionConverter());
		Converters.add(new SimpleTypeCollectionConverter());
	}
	
	
	public static Object updateTargetValues(Object source, Object target) throws ConversionException {
			
		if (source == null || null == target) {
			return target;
		}
		
		final Class<?> fromClass = source.getClass();
		final Class<?> toClass = target.getClass();
		final String key = RootBuilder.createKey(fromClass, toClass);
		
		if (!BuilderRules.containsKey(key)) {
			throw new ConversionException("Builder for key '"+key+"' is not registered");
		}

		final BuilderRuleIf targetTypeBuilder = BuilderRules.get(key);
		targetTypeBuilder.apply(source, target);
		return target;
	}
	
	public static synchronized void registerMapping(final Class<?> fromClass, final Class<?> toClass) {
		final RootBuilder rule = createMapping(fromClass, toClass);
		HashMap<String, BuilderRuleIf> newRules = new HashMap<String, BuilderRuleIf>(BuilderRules);
		newRules.put(rule.getKey(), rule);
		BuilderRules = newRules;
	}
	
	private static RootBuilder createMapping(final Class<?> fromClass, final Class<?> toClass) {
		final Collection<BuilderRuleIf> rules = reflectType(fromClass, toClass, "root");
		final RootBuilder rule = new RootBuilder(fromClass, toClass, rules);
		return rule;
	}
	
	public static Collection<BuilderRuleIf> reflectType(final Class<?> fromClass, final Class<?> toClass, final String rootPath) {
		
		final Collection<BuilderRuleIf> ruleSet = new ArrayList<BuilderRuleIf>();
		
		final Flowable<Method> sourceMethodsStream = Flowable
				.fromArray(fromClass.getDeclaredMethods())
				.filter(p -> p.getName().startsWith("get"));
		
		final Flowable<Method> targetMethodsStream = Flowable
				.fromArray(toClass.getDeclaredMethods())
				.filter(p -> p.getName().startsWith("set"));
		
		final List<Method[]> result = sourceMethodsStream
				.flatMap(s -> targetMethodsStream.filter(t -> MatchProperties(s, t)).map(t -> new Method[] { s, t }))
				.toList()
				.blockingGet();

		for (Method[] pair : result) {
			final Method getter = pair[0];
			final Method setter = pair[1];
			final String path = rootPath + "." + getter.getName().substring(3);
			final Class<?> getterType = getter.getReturnType();
			final Class<?> setterType = setter.getParameterTypes()[0];
			
			final BuilderContext ctx = new BuilderContext(path, getter, setter);
			final Optional<ConverterEntryIf> converter = Converters
				.stream()
				.filter(c -> c.canConvert(ctx))
				.sorted(new Comparator<ConverterEntryIf>() {
					public int compare(ConverterEntryIf o1, ConverterEntryIf o2) {
						return o1.getPriority() == o2.getPriority() ? 0 : o1.getPriority() > o2.getPriority() ? 1 : -1;
					};
				})
				.findFirst();

			if (converter.isPresent()) {
				final ConverterEntryIf valueConverter = converter.get();
				final BuilderRuleIf rule = new ConversionBuilderRule(ctx, valueConverter);
				ruleSet.add(rule);
			}
			else {
				final Collection<BuilderRuleIf> childRules = reflectType(getterType, setterType, path);
				final BuilderRuleIf rule = new ComplexObjectBuilderRule(ctx, childRules);
				ruleSet.add(rule);
			}
		}
		return ruleSet;
	}
	
	static boolean MatchProperties(Method sourceProperty, Method targetProperty) {
		final String sourceName = sourceProperty.getName().substring(3);
		final String targetName = targetProperty.getName().substring(3);
		final boolean isMatch = sourceName.equals(targetName);
		return isMatch;
	}
	
	static Object charSequenceToXmlCalendar(final Object input) throws ConversionException {
		try {
			final DatatypeFactory dtf = DatatypeFactory.newInstance();
			final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
			final Date date = format.parse(input.toString());
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			final XMLGregorianCalendar xmlCalendar = dtf.newXMLGregorianCalendar(calendar);
			return xmlCalendar;
		}
		catch (Exception err) {
			throw new ConversionException(err.getMessage(), err); 
		}
	}
}
