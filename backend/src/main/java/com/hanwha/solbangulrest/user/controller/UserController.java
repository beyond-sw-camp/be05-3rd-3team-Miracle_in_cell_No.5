package com.hanwha.solbangulrest.user.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.user.dto.PasswordResetDto;
import com.hanwha.solbangulrest.user.dto.UserNicknameUpdateDto;
import com.hanwha.solbangulrest.user.dto.UserProfileImageUpdateDto;
import com.hanwha.solbangulrest.user.dto.UserResponseDto;
import com.hanwha.solbangulrest.user.service.JoinService;
import com.hanwha.solbangulrest.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

	private final UserService userService;
	private final JoinService joinService;

	@GetMapping("/profile")
	public Result<UserResponseDto> getLoginUserProfile() {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		UserResponseDto user = userService.findUserProfile(loginId);
		return new Result<>(true, "로그인 사용자 정보 조회", user);
	}

	@PatchMapping("/profile/nickname")
	public Result<Void> updateProfile(@RequestBody @Valid UserNicknameUpdateDto userNicknameUpdateDto) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

		userService.updateUserProfile(loginId, userNicknameUpdateDto);
		return new Result<>(true, "사용자 정보 수정", null);
	}

	@PatchMapping("/profile/image")
	public Result<Void> updateProfileImage(@RequestBody @Valid UserProfileImageUpdateDto profileImage) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

		userService.updateProfileImage(loginId, profileImage);
		return new Result<>(true, "프로필 이미지 수정", null);
	}

	@PatchMapping("/profile/password")
	public Result<Void> updatePassword(@RequestBody @Valid PasswordResetDto passwordResetDto) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();

		userService.updatePassword(loginId, passwordResetDto);
		return new Result<>(true, "비밀번호 수정", null);
	}

	@GetMapping("/posts")
	public Result<Page<PostResponseDto>> getPosts(
		@PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		Page<PostResponseDto> posts = userService.findPostsByLoginId(loginId, pageable);
		return new Result<>(true, "사용자 글 목록 조회", posts);
	}

}
