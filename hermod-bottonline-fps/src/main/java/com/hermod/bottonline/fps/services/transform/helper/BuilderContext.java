package com.hermod.bottonline.fps.services.transform.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.reactivex.Flowable;

public class BuilderContext {

	private Method getter;
	private Method setter;
	private String path;
	private String propertyName;
	private Field sourceField;
	private Field targetField;
	private String sourceFieldTypeName;
	private String targetFieldTypeName;
	private Class<?> getterType;
	private Class<?> setterType;
	
	public BuilderContext(String path, Method getter, Method setter) {
		this.propertyName = getter.getName().substring(3);
		this.getter = getter;
		this.setter = setter;
		this.path = path;
		this.sourceField = getUnderlyingField(getter);
		this.targetField = getUnderlyingField(setter);
		this.sourceFieldTypeName = this.sourceField.getType().getName();
		this.targetFieldTypeName = this.targetField.getType().getName();		
		this.getterType = getter.getReturnType();
		this.setterType = setter.getParameterTypes()[0];
	}

	public Method getGetter() {
		return getter;
	}
	
	public Method getSetter() {
		return setter;
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
