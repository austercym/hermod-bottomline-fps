package com.hermod.bottomline.fps.services.transform.helper.converter;

import java.util.Arrays;
import java.util.Optional;

import com.hermod.bottomline.fps.services.transform.helper.BuilderContext;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;

public class StringToEnumConverter implements ConverterEntryIf {

	@Override
	public boolean canConvert(BuilderContext context) {
		return context.getSetterType().isEnum() && CharSequence.class.isAssignableFrom(context.getGetterType());
	}

	@Override
	public ConvertFunction getConvertFunction(BuilderContext context) {
		return (value, targetReference) -> convertValue(value, targetReference, context);
	}

	@Override
	public String getConverterName() {
		return getClass().getName();
	}

	@Override
	public int getPriority() {
		return ConverterPriority.ENUM_TO_STING_CONVERTER_PRIORITY;
	}

	static Object convertValue(final Object source, final Object targetReference, final BuilderContext context) throws ConversionException {
		if (null == source) {
			return null;
		}
		
		final Object[] values = context.getSetterType().getEnumConstants();
		final String input = source.toString();
		
		final Optional<Object> result = Arrays
			.stream(values)
			.filter(v -> input.equals(v.toString()))
			.findFirst();
		
		if (result.isPresent()) {
			return result.get();
		}
		
		throw new ConversionException("Failed to convert input '" + source +"' to enum <" + context.getSetterType().getName() + "> - no valid enum value found!", context);
	}		
}
