package com.hanwha.solbangulrest.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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
