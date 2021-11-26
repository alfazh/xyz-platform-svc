package com.xyz.platformsvc.exception;

public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1733588952904529506L;

	public InvalidRequestException(String message) {
		super(message);
	}
	
	public InvalidRequestException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
