package com.hanwha.solbangulrest.user.dto;

import java.time.LocalDateTime;

import com.hanwha.solbangulrest.user.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

	private Long id;
	private String loginId;
	private String nickname;
	private String gitEmail;
	private String profileImage;
	private Integer solbangul;
	private LocalDateTime createdDateTime;

	public UserResponseDto(User user) {
		this.id = user.getId();
		this.loginId = user.getLoginId();
		this.nickname = user.getNickname();
		this.gitEmail = user.getHanwhaUser().getGitEmail();
		this.profileImage = user.getProfileImage();
		this.solbangul = user.getSolbangul();
		this.createdDateTime = user.getCreatedDateTime();
	}
}
