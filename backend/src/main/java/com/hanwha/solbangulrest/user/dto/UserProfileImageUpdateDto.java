package com.hanwha.solbangulrest.user.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileImageUpdateDto {

	@NotBlank
	private String profileImage;
}
