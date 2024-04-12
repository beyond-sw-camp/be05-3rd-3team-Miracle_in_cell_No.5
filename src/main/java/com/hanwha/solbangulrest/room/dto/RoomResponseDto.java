package com.hanwha.solbangulrest.room.dto;

import com.hanwha.solbangulrest.room.domain.Room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponseDto {

	private String roomName;
	private String introduction;
	private String profileImage;

	public RoomResponseDto(Room room) {
		this.roomName = room.getRoomName();
		this.introduction = room.getIntroduction();
		this.profileImage = room.getUser().getProfileImage();
	}
}
