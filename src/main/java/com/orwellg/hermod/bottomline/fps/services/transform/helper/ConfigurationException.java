package com.orwellg.hermod.bottomline.fps.services.transform.helper;

public class ConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3842973882265717831L;
	
	private Object sender;
	
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConfigurationException(String message, Throwable cause, Object sender) {
		super(message, cause);
		this.sender = sender;
	}
	
	public Object getSender() {
		return sender;
	}

}
