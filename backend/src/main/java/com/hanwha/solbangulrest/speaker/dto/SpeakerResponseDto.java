package com.hanwha.solbangulrest.speaker.dto;

import java.time.LocalDateTime;

import com.hanwha.solbangulrest.speaker.domain.Speaker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeakerResponseDto {
	private Long speakerId;
	private Long userId;
	private String content;
	private LocalDateTime reservationDateTime;

	public SpeakerResponseDto(Speaker speaker) {
		this.speakerId = speaker.getId();
		this.userId = speaker.getUser().getId();
		this.content = speaker.getContent();
		this.reservationDateTime = speaker.getStartTime();
	}
}
