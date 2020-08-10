package com.chase.useraccessmanagement.service;

import static org.hamcrest.CoreMatchers.anything;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chase.useraccessmanagement.config.KeycloakAdminClientUtils;
import com.chase.useraccessmanagement.config.KeycloakClientConfig;
import com.chase.useraccessmanagement.controller.DataBuilder;
import com.chase.useraccessmanagement.events.UserDetailsQueue;

//@RunWith(MockitoJUnitRunner.class)
public class AccessManagementServiceTest {
	
	@InjectMocks
	AccessManagementService accessManagementService;
	
	@Mock
	KeycloakClientConfig keycloakClientConfig;
	
	@Mock
	KeycloakAdminClientUtils keycloakAdminClientUtils;
	
	@Mock
	UserDetailsQueue userDetailsQueue;
	
	@Mock
	Keycloak keycloak;
	
	
	
//	@Test
//	@SuppressWarnings({ "rawtypes", "static-access" })
	public void testCreateUser() {
//		Keycloak keycloak = KeycloakBuilder.builder()  
//                .serverUrl("http://dev.chase.wavelabs.in/auth/") 
//                .realm("chase-dev") 
//                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
//                .clientId("user-management")
//                .clientSecret("0959b315-e3d9-4940-9a67-9d14ea5439a0") 
//                .build();
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		KeycloakPrincipal principal = Mockito.mock(KeycloakPrincipal.class);
		Keycloak keycloak = Mockito.mock(Keycloak.class);
		RealmResource realmResource = Mockito.mock(RealmResource.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(principal);
//		Mockito.when(keycloak.realm(DataBuilder.getClientCredentials().getKeycloakRealm())).thenReturn(realmResource);
		when(keycloakClientConfig.loadKeycloakConfiguration()).thenReturn(DataBuilder.getClientCredentials());
		when(keycloakAdminClientUtils.getKeycloakClient(principal.getKeycloakSecurityContext(), DataBuilder.getClientCredentials())).thenReturn(keycloak.getInstance( "http://dev.chase.wavelabs.in/auth/", "chase-dev", "orgadmin", "7865", "user-management"));
		accessManagementService.createUser(DataBuilder.getUserRequest());
	}
}
