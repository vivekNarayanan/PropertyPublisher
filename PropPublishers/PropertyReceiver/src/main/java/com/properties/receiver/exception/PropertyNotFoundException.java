package com.properties.receiver.exception;

public class PropertyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyNotFoundException() {
		super();
	}

	public PropertyNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PropertyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyNotFoundException(String message) {
		super(message);
	}

	public PropertyNotFoundException(Throwable cause) {
		super(cause);
	}
	

}
