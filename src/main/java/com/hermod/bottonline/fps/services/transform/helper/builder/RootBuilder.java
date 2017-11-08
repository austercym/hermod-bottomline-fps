package com.hermod.bottonline.fps.services.transform.helper.builder;

import java.util.Collection;

import com.hermod.bottonline.fps.services.transform.helper.ConversionException;

public class RootBuilder implements BuilderRuleIf {
	private String key;
	private Collection<BuilderRuleIf> rules;
			
	public RootBuilder(final Class<?> fromClass, final Class<?> toClass, final Collection<BuilderRuleIf> rules) {
		this.key = createKey(fromClass, toClass);
		this.rules = rules;		
	}
	
	public String getKey() {
		return key;
	}

	@Override
	public void apply(Object source, Object target) throws ConversionException {
		for (BuilderRuleIf rule : rules) {
			rule.apply(source, target);
		}
	}

	@Override
	public String toString() {
		return getKey() + " " + hashCode();
	}
	
	public static String createKey(final Class<?> from, final Class<?> to) {
		return from.getName() + "_" + to.getName();
	}
}
