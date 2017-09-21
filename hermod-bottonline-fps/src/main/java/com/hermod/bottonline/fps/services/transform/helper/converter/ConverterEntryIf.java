package com.hermod.bottonline.fps.services.transform.helper.converter;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;

public interface ConverterEntryIf {
	boolean canConvert(final BuilderContext context);
	
	ConvertFunction getConvertFunction(final BuilderContext context);

	String getConverterName();
	
	int getPriority();
}
