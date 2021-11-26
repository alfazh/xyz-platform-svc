package com.xyz.platformsvc.exception;

public class PlatformServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlatformServiceException(String message) {
		super(message);
	}

	public PlatformServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
