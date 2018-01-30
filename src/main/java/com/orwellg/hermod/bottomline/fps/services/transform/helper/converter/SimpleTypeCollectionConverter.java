package com.orwellg.hermod.bottomline.fps.services.transform.helper.converter;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.BuilderContext;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class SimpleTypeCollectionConverter implements ConverterEntryIf {

	@Override
	public boolean canConvert(BuilderContext context) {
		boolean isCollection = 
				Iterable.class.isAssignableFrom(context.getGetterType()) && 
				Iterable.class.isAssignableFrom(context.getSetterType());
		boolean isSupported = false;
		if (isCollection) {
			final ParameterizedType sourceFieldType = (ParameterizedType)context.getSourceField().getGenericType();
			final Class<?> sourceFieldClass = (Class<?>) sourceFieldType.getActualTypeArguments()[0];
			
			final ParameterizedType targetFieldType = (ParameterizedType)context.getTargetField().getGenericType();
			final Class<?> targetFieldClass = (Class<?>) targetFieldType.getActualTypeArguments()[0];

			isSupported = targetFieldClass.isAssignableFrom(sourceFieldClass) && canBeCopied(sourceFieldClass);
			
		}
				
		return isSupported;
	}

	@Override
	public ConvertFunction getConvertFunction(final BuilderContext context) {		
		return (source, targetReference) -> copyCollection(source, targetReference, context);
	}
	
	@Override
	public String getConverterName() {
		return getClass().getName();
	}

	@Override
	public int getPriority() {
		return ConverterPriority.SIMPLE_TYPE_COLLECTION_CONVERTER_PRIORITY;
	}
	
	static Object copyCollection(final Object source, final Object targetReference, final BuilderContext context) throws ConversionException {
		if (null == source) return null;
		final Collection<?> collection = (Collection<?>)source;
		if (collection.isEmpty()) return null;
		
		try {
			int items = 0;
			final Collection<Object> result = (Collection<Object>)context.createTargetObject(targetReference);
			for (Object item: (Collection<?>)collection) {
				++items;
				result.add(item);
			}
			return items == 0 ? null : result;	
		}
		catch (Exception err) {
			throw new ConversionException("Failed to apply conversion at '" + context.getPath() + "' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, context);
		}
	}
	
	private static boolean canBeCopied(final Class<?> type) {
		boolean result = 
			CharSequence.class.isAssignableFrom(type) || 
			type.isPrimitive() ||
			Number.class.isAssignableFrom(type);
		return result;
	}
}
