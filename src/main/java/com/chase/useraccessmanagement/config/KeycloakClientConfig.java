package com.chase.useraccessmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.chase.useraccessmanagement.model.ClientCredentials;

@Configuration
public class KeycloakClientConfig {

	@Value("${keycloak.auth-server-url}")
	private String keycloakAuthUrl;
	@Value("${keycloak.realm}")
	private String keycloakRealm;
	@Value("${keycloak.resource}")
	private String keycloakResource;

	public ClientCredentials loadKeycloakConfiguration() {
		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setKeycloakAuthUrl(keycloakAuthUrl);
		clientCredentials.setKeycloakRealm(keycloakRealm);
		clientCredentials.setKeycloakResource(keycloakResource);
		return clientCredentials;
	}

}
