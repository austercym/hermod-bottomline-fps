package com.hermod.bottonline.fps.services.transform.helper.converter;

public final class ConverterPriority {
	private ConverterPriority() {		
	}
	
	public static final int CUSTOM_CONVERTER_PRIORITY = 1;
	public static final int ENUM_TO_STING_CONVERTER_PRIORITY = 10;
	public static final int SIMPLE_TYPE_COLLECTION_CONVERTER_PRIORITY = 1000;
	public static final int COMPLEX_TYPE_COLLECTION_CONVERTER_PRIORITY = 10000;
	public static final int MATCHING_TYPE_CONVERTER_PRIORITY = 100000;
}
