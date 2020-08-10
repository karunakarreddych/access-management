package com.chase.useraccessmanagement.request;

import javax.validation.constraints.NotEmpty;

public class PasswordRequest {

	@NotEmpty(message = "Current Password cannot be empty")
	private String currentPassword;
	@NotEmpty(message = "New Password cannot empty")
	private String newPassword;
	@NotEmpty(message = "Confirmation Password cannot be empty")
	private String confirmation;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

}
