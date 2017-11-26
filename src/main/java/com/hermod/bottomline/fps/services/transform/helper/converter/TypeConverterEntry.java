package com.hermod.bottomline.fps.services.transform.helper.converter;

import com.hermod.bottomline.fps.services.transform.helper.BuilderContext;

public class TypeConverterEntry implements ConverterEntryIf {
	
	private Class<?> sourceClass;
	private Class<?> targetClass;
	private ConvertFunctionExtended convertMethod;
	
	public TypeConverterEntry(Class<?> sourceClass, Class<?> targetClass, ConvertFunctionExtended convertMethod) {
		this.sourceClass = sourceClass;
		this.targetClass = targetClass;
		this.convertMethod = convertMethod;
	}

	@Override
	public boolean canConvert(final BuilderContext context) {
		return context.getGetterType().isAssignableFrom(sourceClass) 
			&& context.getSetterType().isAssignableFrom(targetClass);
	}

	@Override
	public int getPriority() {
		return ConverterPriority.CUSTOM_CONVERTER_PRIORITY;
	}

	@Override
	public ConvertFunction getConvertFunction(final BuilderContext context) {
		return (value, targetReference) -> this.convertMethod.convert(value, context);
	}
  
	@Override
	public String getConverterName() {
		return getClass().getName();
	}
	
}
