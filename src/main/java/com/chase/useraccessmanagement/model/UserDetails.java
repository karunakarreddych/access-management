package com.chase.useraccessmanagement.model;

import java.io.Serializable;

public class UserDetails implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private String event_type;
	private Integer mode;
	private String email;
	private UserData data;

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public UserData getData() {
		return data;
	}

	public void setData(UserData data) {
		this.data = data;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
