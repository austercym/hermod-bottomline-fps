package com.hermod.bottonline.fps.services.transform.helper.converter;

import com.hermod.bottonline.fps.services.transform.helper.ConversionException;

@FunctionalInterface
public interface ConvertFunction {
	Object convert(final Object source, final Object targetReference) throws ConversionException;
}
