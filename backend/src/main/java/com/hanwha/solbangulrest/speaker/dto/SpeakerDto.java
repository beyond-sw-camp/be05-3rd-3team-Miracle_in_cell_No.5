package com.hanwha.solbangulrest.speaker.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.hanwha.solbangulrest.speaker.domain.Speaker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeakerDto {

	@NotBlank(message = "내용을 입력해주세요")
	private String content;

	@NotBlank(message = "날짜를 입력해주세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@NotBlank(message = "시간을 입력해주세요")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;

	public SpeakerDto(Speaker speaker) {
		this.reservationDate = speaker.getStartTime().toLocalDate();
		this.reservationTime = speaker.getStartTime().toLocalTime();
		this.content = speaker.getContent();
	}
}
