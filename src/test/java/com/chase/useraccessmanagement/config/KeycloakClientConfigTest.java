package com.chase.useraccessmanagement.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class KeycloakClientConfigTest {

	@InjectMocks
	KeycloakClientConfig keycloakClientConfig;
	
	@Before
	public void setUp() {
		ReflectionTestUtils.setField(keycloakClientConfig, "keycloakAuthUrl", "http://dev.chase.wavelabs.in/auth/");
		ReflectionTestUtils.setField(keycloakClientConfig, "keycloakRealm", "chase-dev");
		ReflectionTestUtils.setField(keycloakClientConfig, "keycloakResource", "user-management");
	}
	
	@Test
	public void testLoadKeycloakConfiguration() {
		keycloakClientConfig.loadKeycloakConfiguration();
	}
}
