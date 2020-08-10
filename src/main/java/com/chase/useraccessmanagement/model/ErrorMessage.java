package com.chase.useraccessmanagement.model;

import java.time.Instant;

public class ErrorMessage {

	private Instant timeStamp;
	private String error;
	private String error_message;
	
	
	public ErrorMessage(Instant timeStamp, String error, String error_message) {
		super();
		this.timeStamp = timeStamp;
		this.error = error;
		this.error_message = error_message;
	}
	
	
	public Instant getTimeStamp() {
		return timeStamp;
	}
	public String getError() {
		return error;
	}
	public String getError_message() {
		return error_message;
	}
	
	
}
