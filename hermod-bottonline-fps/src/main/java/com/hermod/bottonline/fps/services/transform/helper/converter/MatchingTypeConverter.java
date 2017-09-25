package com.hermod.bottonline.fps.services.transform.helper.converter;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;

public class MatchingTypeConverter implements ConverterEntryIf{

	@Override
	public boolean canConvert(final BuilderContext context) {
		return context.getSetterType().isAssignableFrom(context.getGetterType());
	}

	@Override
	public int getPriority() {
		return ConverterPriority.MATCHING_TYPE_CONVERTER_PRIORITY;
	}

	@Override
	public ConvertFunction getConvertFunction(final BuilderContext context) {
		return MatchingTypeConverter::convertValue;
	}

	@Override
	public String getConverterName() {
		return getClass().getName();
	}

	private static Object convertValue(final Object source, final Object targetReference) {
		return source;
	}

}
