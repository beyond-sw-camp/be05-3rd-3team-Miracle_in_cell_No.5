package com.hanwha.solbangulrest.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.UserResponseDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto getUserProfile(Long id) {
		User user = userRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
		);

		return new UserResponseDto(user);
	}
}
