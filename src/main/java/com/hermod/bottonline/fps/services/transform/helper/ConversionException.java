package com.hermod.bottonline.fps.services.transform.helper;

public class ConversionException extends Exception {

	private static final long serialVersionUID = -7063635407775194423L;
	private Object sender;

	public ConversionException(String message, Throwable cause, Object sender) {
		super(message, cause);
		this.sender = sender;
	}
	
	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(String message, Object sender) {
		super(message);
		this.sender = sender;
	}
	
	public Object getSender() {
		return this.sender;
	}

}
