package com.c88.sms;

public enum SmsResultEnum {

	SUCCESS(1, "success"),
	MOBILE_ERROR(2, "error.mobileError"),// message is mapping to i18n code
	UNKNOWN(999, "unknown");

	private int status;
	private String message;

	SmsResultEnum(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return message;
	}
}
