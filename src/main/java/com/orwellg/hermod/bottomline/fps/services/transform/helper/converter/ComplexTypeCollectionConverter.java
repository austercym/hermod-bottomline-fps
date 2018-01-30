package com.orwellg.hermod.bottomline.fps.services.transform.helper.converter;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.BuilderContext;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.builder.BuilderRuleIf;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

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
	public ConvertFunction getConvertFunction(final BuilderContext context) throws ConfigurationException {

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
			public Object convert(final Object inputCollection, final Object targetReference) throws ConversionException {
				if (null == inputCollection) return null;
				
				try {
					int items = 0;
					final Collection<Object> result = (Collection<Object>)context.createTargetObject(targetReference);
					for (Object item: (Collection<?>)inputCollection) {
						++items;
						Object target = targetFieldClass.newInstance();
						for (BuilderRuleIf rule : rules) {
							try {
								rule.apply(item, target);
							}
							catch (ConversionException err) {
								throw err;
							}
							catch (Exception err) {
								throw new ConversionException("Failed to apply conversion at '" + context.getPath() + "[" + (items-1) + "]' due to" + err.getMessage() + " <" + err.getClass().getName() + ">", err, rule);
							}
						}
						result.add(target);
					}
					return items == 0 ? null : result;
				}
				catch (ConversionException err) {
					throw err;
				}
				catch (Exception err) {
					throw new ConversionException("Failed to apply conversion at '"+context.getPath()+"' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, this);
				}
				
			}			
		};
	}

	@Override
	public String getConverterName() {
		return getClass().getName();
	}
}
