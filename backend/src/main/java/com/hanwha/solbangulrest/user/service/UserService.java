package com.hanwha.solbangulrest.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.repository.PostRepository;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.PasswordResetDto;
import com.hanwha.solbangulrest.user.dto.UserNicknameUpdateDto;
import com.hanwha.solbangulrest.user.dto.UserProfileImageUpdateDto;
import com.hanwha.solbangulrest.user.dto.UserResponseDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PostRepository postRepository;

	public UserResponseDto findUserProfile(String loginId) {
		User user = userRepository.findByLoginId(loginId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
		);

		return new UserResponseDto(user);
	}

	@Transactional
	public void updateUserProfile(String loginId, UserNicknameUpdateDto userNicknameUpdateDto) {
		User user = userRepository.findByLoginId(loginId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
		);

		if (user.getNickname().equals(userNicknameUpdateDto.getNickname())) {
			throw new IllegalArgumentException("기존 닉네임과 동일합니다.");
		}
		if (userRepository.existsByNickname(userNicknameUpdateDto.getNickname())) {
			throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
		}

		user.updateNickname(userNicknameUpdateDto.getNickname());
	}

	@Transactional
	public void updateProfileImage(String loginId, UserProfileImageUpdateDto profileImage) {
		User user = userRepository.findByLoginId(loginId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
		);
		user.updateProfileImage(profileImage.getProfileImage());
	}

	@Transactional
	public void updatePassword(String loginId, PasswordResetDto passwordResetDto) {
		User user = userRepository.findByLoginId(loginId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
		);

		if (!passwordEncoder.matches(passwordResetDto.getCurrentPassword(), user.getPassword())) {
			throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
		}

		if (!passwordResetDto.getResetPassword().equals(passwordResetDto.getConfirmResetPassword())) {
			throw new IllegalArgumentException("비밀번호 확인 값이 일치하지 않습니다.");
		}

		user.updatePassword(passwordEncoder.encode(passwordResetDto.getResetPassword()));
	}

	@Transactional
	public Page<PostResponseDto> findPostsByLoginId(String loginId, Pageable pageable) {
		User user = userRepository.findByLoginId(loginId).orElseThrow(
			() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
		);

		Page<Post> posts = postRepository.findPostsByRoomIdPaging(user.getRoom().getId(), pageable);

		return posts.map(PostResponseDto::new);
	}
}
