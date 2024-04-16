package com.hanwha.solbangulrest.global.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

	private Boolean isSuccess;
	private String message;
	private T data;

	public Result(Boolean isSuccess, String message, T data) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = data;
	}
}
