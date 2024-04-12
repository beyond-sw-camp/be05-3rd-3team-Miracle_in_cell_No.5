package com.hanwha.solbangulrest.speaker.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hanwha.solbangulrest.speaker.domain.Speaker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeakerDto {

	private LocalDate reservationDate;
	private LocalTime reservationTime;
	private String content;
	private String loginId;

	public SpeakerDto(Speaker speaker) {
		this.reservationDate = speaker.getStartTime().toLocalDate();
		this.reservationTime = speaker.getStartTime().toLocalTime();
		this.content = speaker.getContent();
	}
}
