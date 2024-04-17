package com.hanwha.solbangulrest.speaker.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpeakerRequestDateDto {

	@NotBlank(message = "날짜를 입력해주세요")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate reservationDate;
}
