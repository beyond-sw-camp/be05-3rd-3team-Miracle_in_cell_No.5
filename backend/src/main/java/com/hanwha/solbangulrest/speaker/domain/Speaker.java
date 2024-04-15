package com.hanwha.solbangulrest.speaker.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.hanwha.solbangulrest.BaseTimeEntity;
import com.hanwha.solbangulrest.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Speaker extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "speaker_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "speaker_content")
	private String content;

	private LocalDateTime startTime;
	private LocalDateTime endTime;

	@Builder
	public Speaker(User user, String content, LocalDateTime startTime, LocalDateTime endTime) {
		this.user = user;
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public void update(String content, LocalDateTime startTime, LocalDateTime endTime) {
		this.content = content;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
