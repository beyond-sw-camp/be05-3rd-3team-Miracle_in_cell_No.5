package com.hanwha.solbangulrest.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.hanwha.solbangulrest.BaseTimeEntity;
import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.room.domain.Room;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "room_id")
	private Room room;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hanwha_user_id")
	private HanwhaUser hanwhaUser;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String loginId;
	private String password;
	private String nickname;
	private String profileImage;
	private Integer solbangul;

	@Builder
	public User(Room room, HanwhaUser hanwhaUser, String loginId, String password, String nickname, String profileImage,
		Role role) {
		this.room = room;
		this.hanwhaUser = hanwhaUser;
		this.loginId = loginId;
		this.password = password;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.role = role;
		this.solbangul = 0;
	}

	public void updateProfile(String nickname, String profileImage) {
		this.nickname = nickname;
		this.profileImage = profileImage;
	}

	public void updatePassword(String password) {
		this.password = password;
	}

	// 연관관계 편의 메서드
	public void addRoom(Room room) {
		this.room = room;
		room.setUser(this);
	}

	public void addSolbangul(Integer solbangul) {
		log.info("{}님의 솔방울이 {}개 올랐습니다.", loginId, solbangul);
		this.solbangul += solbangul;
	}

	public void minusSolbangul(Integer solbangul) {
		if ((this.solbangul - solbangul) < 0) {
			throw new IllegalArgumentException("솔방울이 부족합니다.");
		}
		log.info("{}님의 솔방울이 {}개 감소했습니다.", loginId, solbangul);
		this.solbangul -= solbangul;
	}
}
