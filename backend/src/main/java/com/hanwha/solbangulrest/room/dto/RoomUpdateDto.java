package com.hanwha.solbangulrest.room.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomUpdateDto {

	@NotBlank(message = "방 이름을 입력해주세요")
	private String roomName;

	@NotBlank(message = "방 소개를 입력해주세요")
	private String introduction;

	public RoomUpdateDto(String roomName, String introduction) {
		this.roomName = roomName;
		this.introduction = introduction;
	}
}
