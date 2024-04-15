package com.hanwha.solbangulrest.speaker.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.speaker.dto.SpeakerDto;
import com.hanwha.solbangulrest.speaker.service.SpeakerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/speakers")
@RestController
public class SpeakerController {

	private final SpeakerService speakerService;

	@PostMapping
	public void save(@RequestBody @Valid SpeakerDto speakerDto) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		speakerService.saveSpeaker(loginId, speakerDto);
	}

	@GetMapping
	public List<SpeakerDto> findAll() {
		return speakerService.findAll();
	}
}
