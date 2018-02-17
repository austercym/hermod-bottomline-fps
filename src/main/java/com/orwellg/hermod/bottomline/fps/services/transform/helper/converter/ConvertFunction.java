package com.orwellg.hermod.bottomline.fps.services.transform.helper.converter;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;

@FunctionalInterface
public interface ConvertFunction {
	Object convert(final Object source, final Object targetReference) throws ConversionException;
}
