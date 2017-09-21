package com.hermod.bottonline.fps.services.transform.helper.converter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;
import com.hermod.bottonline.fps.services.transform.helper.ConversionException;

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
		
		return new ConvertFunction() {
			
			@Override
			public Object convert(Object inputCollection) throws ConversionException {
				if (null == inputCollection) return null;
				
				int items = 0;
				final Collection<Object> result = new ArrayList<Object>();
				for (Object item: (Collection<?>)inputCollection) {
					++items;
					result.add(item);
				}
				return items == 0 ? null : result;
			}			
		};
	}
	
	@Override
	public String getConverterName() {
		return getClass().getName();
	}

	@Override
	public int getPriority() {
		return ConverterPriority.SIMPLE_TYPE_COLLECTION_CONVERTER_PRIORITY;
	}
	
	static Object copyCollection(final Object source) {
		if (null == source) return null;
		final Collection<?> collection = (Collection<?>)source;
		if (collection.isEmpty()) return null;
		
		final Collection<Object> result = new ArrayList<Object>(collection);
		return result;
	}
	
	private static boolean canBeCopied(final Class<?> type) {
		boolean result = 
			CharSequence.class.isAssignableFrom(type) || 
			type.isPrimitive() ||
			Number.class.isAssignableFrom(type);
		return result;
	}
}
