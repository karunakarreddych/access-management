package com.chase.useraccessmanagement.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakSecurityContext;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.chase.useraccessmanagement.controller.DataBuilder;

@RunWith(MockitoJUnitRunner.class)
public class KeycloakAdminClientUtilsTest {

	@InjectMocks
	KeycloakAdminClientUtils keycloakAdminClientUtils;
	
	@Mock
	KeycloakSecurityContext keycloakSecurityContext;
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(keycloakAdminClientUtils, "clientSecret", "0959b315-e3d9-4940-9a67-9d14ea5439a0");
	}
	@Test
	public void testGetKeycloakClient() {
		keycloakAdminClientUtils.getKeycloakClient(keycloakSecurityContext, DataBuilder.getClientCredentials());
	}
}
