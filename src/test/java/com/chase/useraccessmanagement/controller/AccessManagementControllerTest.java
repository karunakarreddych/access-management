package com.chase.useraccessmanagement.controller;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import com.chase.useraccessmanagement.exceptions.InvalidDataException;
import com.chase.useraccessmanagement.request.EnableDisableRequest;
import com.chase.useraccessmanagement.request.PasswordRequest;
import com.chase.useraccessmanagement.request.UserRequest;
import com.chase.useraccessmanagement.service.AccessManagementService;

@RunWith(MockitoJUnitRunner.class)
public class AccessManagementControllerTest {

	@InjectMocks
	AccessManagementController accessManagementController;
	
	@Mock
	AccessManagementService accessManagementService;
	
	@Mock
	BindingResult result;
	
	@Test
	public void testCreateUser() {
		accessManagementController.createUser("authToken", new UserRequest());
	}
	
	@Test
	public void testDisableUser() {
		accessManagementController.enableOrdisableUser("authToken", "chase", new EnableDisableRequest());
	}
	
	@Test(expected=InvalidDataException.class)
	public void testUpdatePasswordWhenInvalidDataExceptionThrown() {
		when(result.hasErrors()).thenReturn(true);
		accessManagementController.updatePassword("authToken", new PasswordRequest(), result);
	}
	
	@Test
	public void testUpdatePassword() {
		accessManagementController.updatePassword("authToken", DataBuilder.getPasswordRequest(), result);
	}
	
}
