package com.hermod.bottonline.fps.services.transform.helper.builder;

import java.util.Collection;

import com.hermod.bottonline.fps.services.transform.helper.BuilderContext;
import com.hermod.bottonline.fps.services.transform.helper.ConversionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.parsing.SourceExtractor;

public class ComplexObjectBuilderRule implements BuilderRuleIf {

	private final static Logger LOG = LogManager.getLogger(ComplexObjectBuilderRule.class);

	private BuilderContext ctx;
	private Collection<BuilderRuleIf> childBuilders;

	public ComplexObjectBuilderRule(final BuilderContext context, final Collection<BuilderRuleIf> childBuilders ) {
		this.ctx = context;
		this.childBuilders = childBuilders;
	}	
	@Override
	public void apply(Object source, Object target) throws ConversionException {
		try {
			final Object sourceValue = this.ctx.getGetter().invoke(source);
			if (sourceValue == null) return;
			
			final Object targetValue = this.ctx.createTargetObject(target);

			
			for (BuilderRuleIf child : childBuilders) {
				child.apply(sourceValue, targetValue);
			}
			
			this.ctx.updateTargetObject(target, targetValue);			
		}
		catch (ConversionException err) {
			throw err;
		}
		catch (Exception err) {
			throw new ConversionException(source.getClass()+"----"+target.getClass()+" Failed to apply conversion at '"+this.ctx.getPath()+"' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, this);
		}
	}
	
	@Override
	public String toString() {
		return "ComplexObjectBuilderRule for '" + this.ctx.getPath() + "' " + this.hashCode();
	}
}
