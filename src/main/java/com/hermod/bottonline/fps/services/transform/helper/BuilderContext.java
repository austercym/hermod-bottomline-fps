package com.hermod.bottonline.fps.services.transform.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.Flowable;

public class BuilderContext {

	private Method sourceValueAccessor;
	private Method targetValueAccessor;
	private String path;
	private String propertyName;
	private Field sourceField;
	private Field targetField;
	private String sourceFieldTypeName;
	private String targetFieldTypeName;
	private Class<?> getterType;
	private Class<?> setterType;
	private boolean propertyIsReadonly;
	
	public BuilderContext(String path, Method sourceValueAccessor, Method targetValueAccessor) {
		this.propertyName = sourceValueAccessor.getName().substring(3);
		this.sourceValueAccessor = sourceValueAccessor;
		this.targetValueAccessor = targetValueAccessor;
		this.path = path;
		this.sourceField = getUnderlyingField(sourceValueAccessor);
		this.targetField = getUnderlyingField(targetValueAccessor);
		this.sourceFieldTypeName = this.sourceField.getType().getName();
		this.targetFieldTypeName = this.targetField.getType().getName();		
		this.getterType = sourceValueAccessor.getReturnType();
		
		if (targetValueAccessor.getName().startsWith("set")) {
			this.setterType = targetValueAccessor.getParameterTypes()[0];
			this.propertyIsReadonly = false;
		} else {
			this.setterType = targetValueAccessor.getReturnType();
			this.propertyIsReadonly = true;
		}
	}
	
	public Object createTargetObject(final Object containerObject) throws InvocationTargetException, IllegalAccessException, InstantiationException {
		if (propertyIsReadonly) {
			return targetValueAccessor.invoke(containerObject);
		} else if (Collection.class.isAssignableFrom(setterType)){
			return new ArrayList<Object>();
		} else {
			return setterType.newInstance();			
		}
	}
	
	public void updateTargetObject(final Object containerObject, final Object targetValue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (propertyIsReadonly) return;
		targetValueAccessor.invoke(containerObject, targetValue);
	}

	public Method getGetter() {
		return sourceValueAccessor;
	}

	public String getPath() {
		return path;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public Field getSourceField() {
		return sourceField;
	}
	
	public Field getTargetField() {
		return targetField;
	}
	
	public String getSourcePropertyTypeName() {
		return sourceFieldTypeName;
	}

	public String getTargetPropertyTypeName() {
		return targetFieldTypeName;
	}
	
	public Class<?> getGetterType() {
		return getterType;
	}
	
	public Class<?> getSetterType() {
		return setterType;
	}

	static Field getUnderlyingField(Method method) {
		final String name = method.getName().substring(3).toLowerCase();
		final Class<?> declaringType = method.getDeclaringClass();
		final Field[] fields = declaringType.getDeclaredFields();
		final Field field = Flowable
			.fromArray(fields)
			.filter(f -> f.getName().toLowerCase().equals(name))
			.blockingFirst();
		return field;
	}		
	
}
