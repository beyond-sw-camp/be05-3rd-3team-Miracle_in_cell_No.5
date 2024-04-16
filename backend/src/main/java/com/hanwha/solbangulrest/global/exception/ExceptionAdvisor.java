package com.hanwha.solbangulrest.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.user.exception.MailCheckException;

@RestControllerAdvice
public class ExceptionAdvisor {

	@ExceptionHandler
	public Result<Map<String, String>> processValidationError(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();

		BindingResult bindingResult = e.getBindingResult();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return new Result<>(false, "유효성 검사 실패", errors);
	}

	@ExceptionHandler
	public Result<Void> processValidationError(IllegalArgumentException e) {
		return new Result<>(false, e.getMessage(), null);
	}

	@ExceptionHandler
	public Result<Void> mailCheckExceptionHandler(MailCheckException e) {
		return new Result<>(false, e.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler
	public Result<Void> exceptionHandler(Exception e) {
		return new Result<>(false, e.getMessage(), null);
	}
}
