package com.chase.useraccessmanagement.controller;

import java.io.Serializable;

import javax.validation.Valid;

import org.keycloak.admin.client.Keycloak;

import com.chase.useraccessmanagement.model.ClientCredentials;
import com.chase.useraccessmanagement.request.EnableDisableRequest;
import com.chase.useraccessmanagement.request.PasswordRequest;
import com.chase.useraccessmanagement.request.UserRequest;

public class DataBuilder implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static @Valid PasswordRequest getPasswordRequest() {
		PasswordRequest passwordRequest = new PasswordRequest();
		passwordRequest.setConfirmation("abcd");
		passwordRequest.setCurrentPassword("xyz");
		passwordRequest.setNewPassword("abcd");
		return passwordRequest;
	}

	public static UserRequest getUserRequest() {
		UserRequest userRequest = new UserRequest();
		userRequest.setFirstName("chase");
		userRequest.setLastName("dev");
		userRequest.setEmail("abc@gmail.com");
		userRequest.setUsername("chased");
		return userRequest;
	}

	public static ClientCredentials getClientCredentials() {
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setKeycloakAuthUrl("http://dev.chase.wavelabs.in/auth/");
		clientCredentials.setKeycloakRealm("chase-dev");
		clientCredentials.setKeycloakResource("user-management");
		return clientCredentials;
	}

	public static Keycloak getKeycloak() {
		return Keycloak.getInstance("", "", "", "");
	}

	public static EnableDisableRequest getDisableRequest() {
		EnableDisableRequest EnableDisableRequest = new EnableDisableRequest();
		EnableDisableRequest.setUsername("psri");
		EnableDisableRequest.setActive(false);
		return EnableDisableRequest;
	}

	public static EnableDisableRequest getEnableRequest() {
		EnableDisableRequest enableDisableRequest = new EnableDisableRequest();
		enableDisableRequest.setUsername("psri");
		enableDisableRequest.setActive(true);
		return enableDisableRequest;
	}

	

}
