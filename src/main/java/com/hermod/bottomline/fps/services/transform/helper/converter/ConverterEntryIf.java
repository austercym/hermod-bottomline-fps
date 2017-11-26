package com.hermod.bottomline.fps.services.transform.helper.converter;

import com.hermod.bottomline.fps.services.transform.helper.BuilderContext;
import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;

public interface ConverterEntryIf {
	boolean canConvert(final BuilderContext context);
	
	ConvertFunction getConvertFunction(final BuilderContext context) throws ConfigurationException;

	String getConverterName();
	
	int getPriority();
}
