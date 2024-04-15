package com.hanwha.solbangulrest.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomUpdateDto {

	private String roomName;
	private String introduction;

	public RoomUpdateDto(String roomName, String introduction) {
		this.roomName = roomName;
		this.introduction = introduction;
	}
}
