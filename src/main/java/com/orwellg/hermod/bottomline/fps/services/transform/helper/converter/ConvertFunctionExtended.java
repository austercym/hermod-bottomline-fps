package com.orwellg.hermod.bottomline.fps.services.transform.helper.converter;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.BuilderContext;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;

@FunctionalInterface
public interface ConvertFunctionExtended {
	Object convert(final Object source, final BuilderContext ctx) throws ConversionException;

}
