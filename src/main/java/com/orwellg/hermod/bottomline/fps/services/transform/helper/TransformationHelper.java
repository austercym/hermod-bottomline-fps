package com.orwellg.hermod.bottomline.fps.services.transform.helper;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.builder.BuilderRuleIf;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.builder.ComplexObjectBuilderRule;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.builder.ConversionBuilderRule;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.builder.RootBuilder;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.converter.*;
import com.orwellg.umbrella.avro.types.commons.Decimal;
import com.orwellg.umbrella.commons.types.utils.avro.DecimalTypeUtils;
import io.reactivex.Flowable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class TransformationHelper {

	private final static Logger LOG = LogManager.getLogger(TransformationHelper.class);

	private static final Collection<ConverterEntryIf> Converters = new ArrayList<>();
	private static HashMap<String, BuilderRuleIf> BuilderRules = new HashMap<>();

	static {
		Converters.add(new TypeConverterEntry(XMLGregorianCalendar.class, Long.class, (calendar, ctx) -> (Long)((XMLGregorianCalendar)calendar).toGregorianCalendar().getTimeInMillis()));
		Converters.add(new TypeConverterEntry(XMLGregorianCalendar.class, CharSequence.class, (calendar, ctx) -> ((XMLGregorianCalendar)calendar).toXMLFormat()));
		Converters.add(new TypeConverterEntry(XMLGregorianCalendar.class, String.class, (calendar, ctx) -> ((XMLGregorianCalendar)calendar).toXMLFormat()));
		Converters.add(new TypeConverterEntry(BigDecimal.class, Decimal.class, (decimalValue, ctx) -> DecimalTypeUtils.toDecimal((BigDecimal)decimalValue,2)));
		Converters.add(new TypeConverterEntry(Decimal.class, BigDecimal.class, (decimalValue, ctx) ->  ((Decimal)decimalValue).getValue()));
		Converters.add(new TypeConverterEntry(CharSequence.class,  XMLGregorianCalendar.class, TransformationHelper::charSequenceToXmlCalendar));
		Converters.add(new TypeConverterEntry(String.class,  XMLGregorianCalendar.class, TransformationHelper::charSequenceToXmlCalendar));
		Converters.add(new TypeConverterEntry(Long.class, XMLGregorianCalendar.class, TransformationHelper::longToXmlCalendar));
		Converters.add(new EnumToStringConverter());
		Converters.add(new StringToEnumConverter());
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
	
	public static synchronized void registerMapping(final Class<?> fromClass, final Class<?> toClass) throws ConfigurationException {
		final RootBuilder rule = createMapping(fromClass, toClass);
		HashMap<String, BuilderRuleIf> newRules = new HashMap<String, BuilderRuleIf>(BuilderRules);
		newRules.put(rule.getKey(), rule);
		BuilderRules = newRules;
	}
	
	private static RootBuilder createMapping(final Class<?> fromClass, final Class<?> toClass) throws ConfigurationException {
		final Collection<BuilderRuleIf> rules = reflectType(fromClass, toClass, "root");
		final RootBuilder rule = new RootBuilder(fromClass, toClass, rules);
		return rule;
	}
	
	public static Collection<BuilderRuleIf> reflectType(final Class<?> fromClass, final Class<?> toClass, final String rootPath) throws ConfigurationException {
		try {
			final Collection<BuilderRuleIf> ruleSet = new ArrayList<BuilderRuleIf>();
			
			final Flowable<Method> sourceMethodsStream = Flowable
					.fromArray(fromClass.getDeclaredMethods())
					.filter(p -> p.getName().startsWith("get"));
			
			final Flowable<Method> targetMethodsStream = Flowable
					.fromArray(toClass.getDeclaredMethods())
					.filter(p -> p.getName().startsWith("set") || p.getName().startsWith("get"))
					.sorted(new Comparator<Method>() {
						@Override
						public int compare(Method o1, Method o2) {
							return o1.getName().compareTo(o2.getName()) * -1;
						}
					});
			
			final List<Method[]> result = sourceMethodsStream
					.flatMap(s -> targetMethodsStream.filter(t -> MatchProperties(s, t)).take(1).map(t -> new Method[] { s, t }))
					.toList()
					.blockingGet();
	
			for (Method[] pair : result) {
				final Method sourceAccessor = pair[0];
				final Method targetAccessor = pair[1];
				final String path = rootPath + "." + sourceAccessor.getName().substring(3);
				final Class<?> getterType = sourceAccessor.getReturnType();
				
				final Class<?> setterType = targetAccessor.getName().startsWith("set") 
						? targetAccessor.getParameterTypes()[0]
						: targetAccessor.getReturnType();
				
				final BuilderContext ctx = new BuilderContext(path, sourceAccessor, targetAccessor);
				final Optional<ConverterEntryIf> converter = Converters
					.stream()
					.filter(c -> c.canConvert(ctx))
					.sorted(new Comparator<ConverterEntryIf>() {
						public int compare(ConverterEntryIf o1, ConverterEntryIf o2) {
							return o1.getPriority() == o2.getPriority() ? 0 : o1.getPriority() > o2.getPriority() ? 1 : -1;
						};
					})
					.findFirst();
	
				try {
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
				catch (ConfigurationException err) {
					throw err;
				}
				catch (Exception err) {
					throw new ConfigurationException("Failed to configure conversion at path '" + path + "' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, ctx);
				}
			}
			return ruleSet;
		}
		catch (ConfigurationException err) {
			throw err;
		}
		catch (Exception err) {
			throw new ConfigurationException("Failed to configure conversion at path '" + rootPath + "' due to " +err.getMessage() + " <" + err.getClass().getName() + ">", err);
		}
	}
	
	static boolean MatchProperties(Method sourceProperty, Method targetProperty) {
		final String sourceName = sourceProperty.getName().substring(3);
		final String targetName = targetProperty.getName().substring(3);
		final boolean isMatch = sourceName.equals(targetName);
		return isMatch;
	}
	
	static Object charSequenceToXmlCalendar(final Object input, final BuilderContext ctx) throws ConversionException {
		try {
			final String inputDate = input.toString();
			
			if (inputDate.length() == 0) {
				return null;
			}
			final DateFormat format;

			if (inputDate.matches("^\\d{2}:\\d{2}:\\d{2}")){
				format = new SimpleDateFormat("hh:mm:ss");
			}else{
				format = new SimpleDateFormat("yyyy-MM-dd");
			}

			final Date date = format.parse(inputDate);
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			
			final DatatypeFactory dtf = DatatypeFactory.newInstance();
			final XMLGregorianCalendar xmlCalendar = dtf.newXMLGregorianCalendar(calendar);
			xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			return xmlCalendar;
		}
		catch (Exception err) {
			throw new ConversionException("Custom conversion CharSequence -> XMLGregorianCalendar failed with '" + err.getMessage() + "' <" + err.getClass().getName() + ">", err, ctx);
		}
	}
	
	static Object longToXmlCalendar(final Object input, final BuilderContext ctx) throws ConversionException {
		try {
			final Long dateAsLong = (Long)input;
			final Date date = new Date(dateAsLong);
			final GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);

			final DatatypeFactory dtf = DatatypeFactory.newInstance();
			final XMLGregorianCalendar xmlCalendar = dtf.newXMLGregorianCalendar(calendar);
			xmlCalendar.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			return xmlCalendar;
		}
		catch (Exception err) {
			throw new ConversionException("Custom conversion Long->XMLGregorianCalendar failed with '" + err.getMessage() + "' <" + err.getClass().getName() +">", err, ctx);
		}
	}
}
