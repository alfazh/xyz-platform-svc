package com.xyz.platformsvc.exception;

public class ServiceError {
	
	private String error;

	public ServiceError(String error) {
		super();
		this.error = error;
	}

	public static ServiceError of(String error) {
		return new ServiceError(error);
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
