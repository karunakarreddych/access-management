package com.chase.useraccessmanagement.util;

public enum Mode {
	EMAIL(1),
	PUSH_NOTIFICATION(2),
	EMAIL_AND_PUSH_NOTIFICATION(3),
	CHAT_MESSAGE(4),
	EMAIL_AND_CHAT_MESSAGE(5),
	PUSH_NOTIFICATION_AND_CHAT_MESSAGE(6),
	EMAIL_AND_PUSH_NOTIFICATION_AND_CHAT_MESSAGE(7);
	
	private final int code;

    private Mode(int code) {
        this.code = code;
    }

	public int getCode() {
		return code;
	}
    
}
