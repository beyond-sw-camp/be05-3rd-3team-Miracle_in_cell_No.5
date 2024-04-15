package com.hanwha.solbangulrest.speaker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.speaker.domain.Speaker;
import com.hanwha.solbangulrest.speaker.dto.SpeakerDto;
import com.hanwha.solbangulrest.speaker.repository.SpeakerRepository;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SpeakerService {

	public static final int SPEAKER_PRICE = 10;

	private final SpeakerRepository speakerRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long saveSpeaker(String loginId, SpeakerDto speakerDto) {
		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. username=" + loginId));

		LocalDateTime startTime = getLocalDateTime(speakerDto);
		Speaker speaker = Speaker.builder()
			.startTime(startTime)
			.endTime(startTime.plusMinutes(30))
			.content(speakerDto.getContent())
			.user(user)
			.build();

		user.minusSolbangul(SPEAKER_PRICE);
		speakerRepository.save(speaker);

		return speaker.getId();
	}

	private static LocalDateTime getLocalDateTime(SpeakerDto speakerDto) {
		LocalDate reservationDate = speakerDto.getReservationDate();
		LocalTime reservationTime = speakerDto.getReservationTime();
		return LocalDateTime.of(reservationDate, reservationTime);
	}

	public List<SpeakerDto> findAll() {
		List<Speaker> speakers = speakerRepository.findAll();
		return speakers.stream().map(SpeakerDto::new).toList();
	}
}
