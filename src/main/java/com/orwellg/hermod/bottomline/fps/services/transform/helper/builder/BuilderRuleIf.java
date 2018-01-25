package com.orwellg.hermod.bottomline.fps.services.transform.helper.builder;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;

public interface BuilderRuleIf {

	void apply(Object source, Object target) throws ConversionException;
}