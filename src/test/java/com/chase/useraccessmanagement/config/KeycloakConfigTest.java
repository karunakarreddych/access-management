package com.chase.useraccessmanagement.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@RunWith(MockitoJUnitRunner.class)
public class KeycloakConfigTest {

	@InjectMocks
	KeycloakConfig keycloakConfig;
	
	@Mock
	KeycloakClientRequestFactory keycloakClientRequestFactory;
	
	@Mock
	KeycloakSpringBootProperties keycloakSpringBootProperties;
	
	@Mock
	KeycloakDeployment keycloakDeployment;
	
	@Mock
	AdapterConfig adapterConfig;
	
	@Mock
	AuthenticationManagerBuilder authenticationManagerBuilder;
	
//	@Test
	public void testKeycloakConfigResolver() {
		keycloakSpringBootProperties.setRealm("chase-dev");
		keycloakSpringBootProperties.setResource("user-management");
		keycloakConfig.keycloakConfigResolver(keycloakSpringBootProperties);
	}
	
	@Test
	public void testKeycloakRestTemplate() {
		keycloakConfig.keycloakRestTemplate();
	}
	
	@Test
	public void testGrantedAuthority() {
		keycloakConfig.grantedAuthority();
	}
	
	@Test
	public void testConfigureGlobal() throws Exception {
		keycloakConfig.configureGlobal(authenticationManagerBuilder);
	}
	
	@Test
	public void testSessionAuthenticationStrategy() {
		keycloakConfig.sessionAuthenticationStrategy();
	}
}
