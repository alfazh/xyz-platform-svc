package com.xyz.platformsvc.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ ResourceNotFoundException.class, PlatformServiceException.class })
	public ResponseEntity<ServiceError> handleServiceExeptions(Exception e, WebRequest webRequest) {

		HttpHeaders headers = new HttpHeaders();

		if (e instanceof ResourceNotFoundException) {
			ResourceNotFoundException rnfException = (ResourceNotFoundException) e;
			return handleResourceNotFoundException(rnfException, headers, HttpStatus.NOT_FOUND, webRequest);
		} else if (e instanceof PlatformServiceException) {
			PlatformServiceException psException = (PlatformServiceException) e;
			return handlePlatformServiceException(psException, headers, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
		} else if (e instanceof InvalidRequestException) {
			InvalidRequestException irException = (InvalidRequestException) e;
			return handleInvalidRequestException(irException, headers, HttpStatus.BAD_REQUEST, webRequest);
		} else {
			return handleException(e, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
		}

	}

	private ResponseEntity<ServiceError> handleInvalidRequestException(InvalidRequestException irException,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return handleException(irException, ServiceError.of(irException.getMessage()), headers, status, webRequest);
	}

	private ResponseEntity<ServiceError> handleResourceNotFoundException(ResourceNotFoundException rnfException,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return handleException(rnfException, ServiceError.of(rnfException.getMessage()), headers, status, webRequest);
	}

	private ResponseEntity<ServiceError> handlePlatformServiceException(PlatformServiceException psException,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return handleException(psException, ServiceError.of(psException.getMessage()), headers, status, webRequest);
	}

	private ResponseEntity<ServiceError> handleException(Exception e, ServiceError serviceError, HttpHeaders headers,
			HttpStatus status, WebRequest webRequest) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			webRequest.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, e, WebRequest.SCOPE_REQUEST);
		}

		return new ResponseEntity<>(serviceError, headers, status);
	}

}
