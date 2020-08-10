package com.chase.useraccessmanagement.errorhandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@RunWith(MockitoJUnitRunner.class)
public class CommonErrorHandlerTest {

	@InjectMocks
	CommonErrorHandler commonErrorHandler;
	
	@Mock
	BindingResult bindingResult;
	
	@Test
	public void testGetFieldErrorResponse() {
		FieldError ObjectError = new FieldError("name","name", "name cannot be empty");
		bindingResult.addError(ObjectError);
		commonErrorHandler.getFieldErrorResponse(bindingResult);
	}
	
	@Test
	public void testFieldErrorResponse() {
		commonErrorHandler.fieldErrorResponse("cannot be empty", new Object());
	}
}
