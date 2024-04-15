package com.hanwha.solbangulrest.user.exception;

public class MailCheckException extends RuntimeException {
	public MailCheckException() {
		super();
	}

	public MailCheckException(String message) {
		super(message);
	}

	public MailCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailCheckException(Throwable cause) {
		super(cause);
	}

	protected MailCheckException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
