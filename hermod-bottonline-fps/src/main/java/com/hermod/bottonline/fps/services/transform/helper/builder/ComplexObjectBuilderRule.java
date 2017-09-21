package com.hermod.bottonline.fps.services.transform.helper.builder;

import java.lang.reflect.Method;
import java.util.Collection;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;
import com.hermod.bottonline.fps.services.transform.helper.ConversionException;

public class ComplexObjectBuilderRule implements BuilderRuleIf {

	private BuilderContext ctx;
	private Class<?> targetValueType;
	private Collection<BuilderRuleIf> childBuilders;

	public ComplexObjectBuilderRule(final BuilderContext context, final Collection<BuilderRuleIf> childBuilders ) {
		this.ctx = context;
		this.childBuilders = childBuilders;
		this.targetValueType = this.ctx.getTargetField().getType();
	}	
	@Override
	public void apply(Object source, Object target) throws ConversionException {
		try {
			final Object sourceValue = this.ctx.getGetter().invoke(source);
			if (sourceValue == null) return;
			
			final Object targetValue = targetValueType.newInstance();
			
			for (BuilderRuleIf child : childBuilders) {
				child.apply(sourceValue, targetValue);
			}
			
			this.ctx.getSetter().invoke(target, targetValue);			
		}
		catch (ConversionException err) {
			throw err;
		}
		catch (Exception err) {
			throw new ConversionException("Failed to apply conversion at '"+this.ctx.getPath()+"' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, this);			
		}
	}
	
	@Override
	public String toString() {
		return "ComplexObjectBuilderRule for '" + this.ctx.getPath() + "' " + this.hashCode();
	}
}
