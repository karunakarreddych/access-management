package com.chase.useraccessmanagement.model;

public class ClientCredentials {

	private String keycloakAuthUrl;
	private String keycloakRealm;
	private String keycloakResource;

	public String getKeycloakAuthUrl() {
		return keycloakAuthUrl;
	}

	public void setKeycloakAuthUrl(String keycloakAuthUrl) {
		this.keycloakAuthUrl = keycloakAuthUrl;
	}

	public String getKeycloakRealm() {
		return keycloakRealm;
	}

	public void setKeycloakRealm(String keycloakRealm) {
		this.keycloakRealm = keycloakRealm;
	}

	public String getKeycloakResource() {
		return keycloakResource;
	}

	public void setKeycloakResource(String keycloakResource) {
		this.keycloakResource = keycloakResource;
	}

}
