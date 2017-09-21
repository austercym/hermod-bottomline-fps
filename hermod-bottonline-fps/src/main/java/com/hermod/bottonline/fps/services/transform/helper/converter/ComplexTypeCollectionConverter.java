package com.hermod.bottonline.fps.services.transform.helper.converter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;
import com.hermod.bottonline.fps.services.transform.helper.ConversionException;
import com.hermod.bottonline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottonline.fps.services.transform.helper.builder.BuilderRuleIf;

public class ComplexTypeCollectionConverter implements ConverterEntryIf {
	
	@Override
	public boolean canConvert(final BuilderContext context) {
		return Iterable.class.isAssignableFrom(context.getGetterType()) 
			&& Iterable.class.isAssignableFrom(context.getSetterType());
	}

	@Override
	public int getPriority() {
		return ConverterPriority.COMPLEX_TYPE_COLLECTION_CONVERTER_PRIORITY;
	}

	@Override
	public ConvertFunction getConvertFunction(final BuilderContext context) {

		final Field sourceField = context.getSourceField();
		final Field targetField = context.getTargetField();
		
		final ParameterizedType sourceFieldType = (ParameterizedType)sourceField.getGenericType();
		final Class<?> sourceFieldClass = (Class<?>) sourceFieldType.getActualTypeArguments()[0];
		
		final ParameterizedType targetFieldType = (ParameterizedType)targetField.getGenericType();
		final Class<?> targetFieldClass = (Class<?>) targetFieldType.getActualTypeArguments()[0];
		
		final Collection<BuilderRuleIf> builderRules = TransformationHelper.reflectType(sourceFieldClass, targetFieldClass, context.getPath() + "[]");

		final BuilderRuleIf[] rules = new BuilderRuleIf[builderRules.size()];
		builderRules.toArray(rules);

		return new ConvertFunction() {
			
			@Override
			public Object convert(Object inputCollection) throws ConversionException {
				if (null == inputCollection) return null;
				
				int items = 0;
				final Collection<Object> result = new ArrayList<Object>();
				try {
					for (Object item: (Collection<?>)inputCollection) {
						++items;
						Object target = targetFieldClass.newInstance();
						for (BuilderRuleIf rule : rules) {
							try {
								rule.apply(item, target);
							}
							catch (Exception err) {
								if (err instanceof ConversionException) {
									throw (ConversionException)err;
								}
								throw new ConversionException("Failed to apply conversion at '" + context.getPath() + "[" + (items-1) + "]' due to" + err.getMessage() + " <" + err.getClass().getName() + ">", err, rule);
							}
						}
						result.add(target);
					}
				}
				catch (ConversionException err) {
					throw err;
				}
				catch (Exception err) {
					throw new ConversionException("Failed to apply conversion at '"+context.getPath()+"' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, this);			
				}
				
				return items == 0 ? null : result;
			}			
		};
		/*
		int i = 0;
		final ArrayList<Object> targetCollection = new ArrayList<Object>();

		for (Object item : (Collection<?>)sourceValue) {
			
			if (targetFieldClass.isAssignableFrom(sourceFieldClass)) {
				targetCollection.add(item);
				++i;
				continue;
			}
			
			final Optional<Function<Object, Object>> opt = tryGetConverter(targetFieldClass, sourceFieldClass);
			
			if (opt.isPresent()) {
				final Object convertedValue = opt.get().apply(item);
				targetCollection.add(convertedValue);
				++i;
				continue;
			}
			
			final Object targetValue = targetFieldClass.newInstance();
			final String location = path + "[" + i + "]";
			deepCopy(sourceFieldClass, targetFieldClass, item, targetValue, location);
			targetCollection.add(targetValue);
			++i;
		}
		if (i > 0) {
			setter.invoke(toInstance, targetCollection);
		}
		
		
		return null;
		*/
	}

	@Override
	public String getConverterName() {
		return getClass().getName();
	}
}
