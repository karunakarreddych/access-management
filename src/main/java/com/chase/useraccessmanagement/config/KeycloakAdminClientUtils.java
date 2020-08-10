package com.chase.useraccessmanagement.config;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chase.useraccessmanagement.model.ClientCredentials;

@Component
public class KeycloakAdminClientUtils {
	@Value("${keyclock.user.management.client.secret}")
	private String clientSecret;
	
	public Keycloak getKeycloakClient(KeycloakSecurityContext session, ClientCredentials clientCredentials) {
        return KeycloakBuilder.builder()  
                .serverUrl(clientCredentials.getKeycloakAuthUrl()) 
                .realm(clientCredentials.getKeycloakRealm()) 
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientCredentials.getKeycloakResource())
                .clientSecret(clientSecret) 
                .authorization(session.getTokenString()) 
                .build();
    }
}
