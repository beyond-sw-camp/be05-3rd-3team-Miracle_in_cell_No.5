package com.hanwha.solbangulrest.speaker.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SpeakerRequestDto {

	@NotBlank
	private String content;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime reservationTime;
}
