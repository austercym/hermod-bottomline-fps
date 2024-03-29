package com.orwellg.hermod.bottomline.fps.services.transform.helper.builder;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.BuilderContext;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.converter.ConvertFunction;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.converter.ConverterEntryIf;

public class ConversionBuilderRule implements BuilderRuleIf {
	private BuilderContext ctx;
	private String converterName;
	private ConvertFunction convertMethod;
	
	public ConversionBuilderRule(final BuilderContext context, final ConverterEntryIf converter) throws ConfigurationException {
		this.ctx = context;
		this.convertMethod = converter.getConvertFunction(context);
		this.converterName = converter.getConverterName();
	}
	
	/* (non-Javadoc)
	 * @see com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionRuleIf#apply(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void apply(final Object source, Object target) throws ConversionException {
		try {
			if (null == source) return;
			MethodAccess access = MethodAccess.get(this.ctx.getGetter().getDeclaringClass());
			int indexMethod = access.getIndex(this.ctx.getGetter().getName());
			final Object sourceValue = access.invoke(source,indexMethod);
			//final Object sourceValue = this.ctx.getGetter().invoke(source);
			if (null == sourceValue) return;
			final Object targetValue = this.convertMethod.convert(sourceValue, target);
			this.ctx.updateTargetObject(target,  targetValue);
		}
		catch (ConversionException err) {
			throw err;
		}
		catch (Exception err) {
			throw new ConversionException("Failed to apply conversion at '" + ctx.getPath() + "' due to " + err.getMessage() + " <" + err.getClass().getName() + ">", err, this);
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionRuleIf#toString()
	 */
	@Override
	public String toString() {
		return "ConversionBuilderRule for '" + ctx.getPath() + "', converter: " + this.converterName +", id: " + this.hashCode();
	}
}
