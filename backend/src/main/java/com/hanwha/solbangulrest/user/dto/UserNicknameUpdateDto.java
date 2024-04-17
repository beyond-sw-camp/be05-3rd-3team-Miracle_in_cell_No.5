package com.hanwha.solbangulrest.user.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNicknameUpdateDto {

	@NotBlank
	private String nickname;
}
