package com.hermod.bottonline.fps.services.transform.helper.converter;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;

public class EnumToStringConverter implements ConverterEntryIf {
	
	public EnumToStringConverter() {
	}
	
	@Override
	public boolean canConvert(final BuilderContext context) {
		return context.getGetterType().isEnum() && CharSequence.class.isAssignableFrom(context.getSetterType());
	}

	@Override
	public int getPriority() {
		return ConverterPriority.ENUM_TO_STING_CONVERTER_PRIORITY;
	}

	@Override
	public ConvertFunction getConvertFunction(final BuilderContext context) {
		return EnumToStringConverter::convertValue;
	}

	@Override
	public String getConverterName() {
		return getClass().getName();
	}

	static Object convertValue(final Object source, final Object targetReference) {
		if (null == source) {
			return null;
		}
		return source.toString();
	}	
}
