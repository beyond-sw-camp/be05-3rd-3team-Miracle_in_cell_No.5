package com.hanwha.solbangulrest.room.dto;

import com.hanwha.solbangulrest.room.domain.Room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomResponseDto {

	private Long id;
	private String roomName;
	private String introduction;
	private String profileImage;
	private String nickname;
	private Integer solbangul;

	public RoomResponseDto(Room room) {
		this.id = room.getId();
		this.roomName = room.getRoomName();
		this.introduction = room.getIntroduction();
		this.profileImage = room.getUser().getProfileImage();
		this.nickname = room.getUser().getNickname();
		this.solbangul = room.getUser().getSolbangul();
	}
}
