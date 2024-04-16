package com.hanwha.solbangulrest.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.user.dto.UserResponseDto;
import com.hanwha.solbangulrest.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping("/{id}")
	public Result<UserResponseDto> getUser(@PathVariable String id) {
		return null;
	}
}
