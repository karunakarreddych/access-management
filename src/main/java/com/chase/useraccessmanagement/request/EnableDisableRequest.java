package com.chase.useraccessmanagement.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EnableDisableRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	private String username;
	private boolean active;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
}
