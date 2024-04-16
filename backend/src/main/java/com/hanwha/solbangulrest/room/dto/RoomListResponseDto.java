package com.hanwha.solbangulrest.room.dto;

import com.hanwha.solbangulrest.room.domain.Room;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomListResponseDto {
	private Long id;

	private String roomName;

	private String introduction;

	@Builder
	public RoomListResponseDto(Long id, String roomName,String introduction) {
		this.id = id ;
		this.roomName = roomName;
		this.introduction = introduction;
	}

	public RoomListResponseDto(Room room) {
		this.id= room.getId() ;
		this.roomName=room.getRoomName() ;
		this.introduction=room.getIntroduction() ;
	}

}
