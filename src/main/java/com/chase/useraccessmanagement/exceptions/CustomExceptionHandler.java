package com.chase.useraccessmanagement.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chase.useraccessmanagement.errorhandler.CommonErrorHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	CommonErrorHandler commonErrorHandler;
	
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<?> invalidDataException(InvalidDataException ex, WebRequest request) {
		return commonErrorHandler.fieldErrorResponse("Error", commonErrorHandler.getFieldErrorResponse(ex.getResult()));
	}
}
