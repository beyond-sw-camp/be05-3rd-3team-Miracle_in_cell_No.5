package com.hanwha.solbangulrest.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MainController {

	private final UserRepository userRepository;

	@GetMapping("/")
	public JoinUserDto home() {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		return JoinUserDto.builder().
			loginId(user.getLoginId())
			.password(user.getPassword())
			.username(user.getHanwhaUser().getUsername())
			.nickname(user.getNickname())
			.gitEmail(user.getHanwhaUser().getGitEmail())
			.profileImage(user.getProfileImage())
			.build();
	}
}
