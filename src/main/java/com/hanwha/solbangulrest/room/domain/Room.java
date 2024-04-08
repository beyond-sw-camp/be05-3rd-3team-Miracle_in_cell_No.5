package com.hanwha.solbangulrest.room.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.hanwha.solbangulrest.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;

	@Setter
	@OneToOne(mappedBy = "room")
	private User user;

	private String roomName;
	private String introduction;

	@Builder
	public Room(String roomName, String introduction, User user) {
		this.roomName = roomName;
		this.introduction = introduction;
		this.user = user;
	}

	public void update(String roomName, String introduction) {
		this.roomName = roomName;
		this.introduction = introduction;
	}
}
