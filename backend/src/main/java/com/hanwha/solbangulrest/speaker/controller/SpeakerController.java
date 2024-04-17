package com.hanwha.solbangulrest.speaker.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.speaker.dto.SpeakerRequestDto;
import com.hanwha.solbangulrest.speaker.dto.SpeakerResponseDto;
import com.hanwha.solbangulrest.speaker.service.SpeakerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/speakers")
@RestController
public class SpeakerController {

	private final SpeakerService speakerService;

	@PostMapping
	public Result<Void> save(@RequestBody @Valid SpeakerRequestDto speakerRequestDto) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		speakerService.saveSpeaker(loginId, speakerRequestDto);

		return new Result<>(true, "확성기 예약 완료", null);
	}

	@GetMapping("/date")
	public Result<List<SpeakerResponseDto>> findSpeakerByLocalDate(@RequestParam String reservationDate) {
		return new Result<>(true, "날짜별 확성기 목록 조회", speakerService.findSpeakerByLocalDate(reservationDate));
	}

	@GetMapping
	public Result<List<SpeakerResponseDto>> findAll() {
		List<SpeakerResponseDto> speakers = speakerService.findAll();
		return new Result<>(true, "모든 확성기 목록 조회", speakers);
	}

	@GetMapping("/now")
	public Result<SpeakerResponseDto> findNowSpeaker() {
		SpeakerResponseDto speakers = speakerService.findNowSpeaker();
		return new Result<>(true, "현재 시간 확성기 조회", speakers);
	}
}
