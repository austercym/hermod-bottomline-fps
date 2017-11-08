package com.hermod.bottonline.fps.services.transform.helper.builder;

import com.hermod.bottonline.fps.services.transform.helper.ConversionException;

public interface BuilderRuleIf {

	void apply(Object source, Object target) throws ConversionException;
}