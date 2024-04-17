package com.hanwha.solbangulrest.speaker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.speaker.domain.Speaker;
import com.hanwha.solbangulrest.speaker.dto.SpeakerRequestDto;
import com.hanwha.solbangulrest.speaker.dto.SpeakerResponseDto;
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
	public void saveSpeaker(String loginId, SpeakerRequestDto speakerRequestDto) {
		validateSpeaker(speakerRequestDto);

		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. username=" + loginId));

		LocalDateTime startTime = getLocalDateTime(speakerRequestDto);
		Speaker speaker = Speaker.builder()
			.startTime(startTime)
			.endTime(startTime.plusMinutes(30))
			.content(speakerRequestDto.getContent())
			.user(user)
			.build();

		user.minusSolbangul(SPEAKER_PRICE);
		speakerRepository.save(speaker);
	}

	private void validateSpeaker(SpeakerRequestDto speakerRequestDto) {
		speakerRepository.findByReservationDate(getLocalDateTime(speakerRequestDto),
				getLocalDateTime(speakerRequestDto).plusMinutes(30))
			.stream()
			.findAny()
			.filter(
				existingSpeaker -> {
					checkIfReservationOverlaps(existingSpeaker, speakerRequestDto);
					return true;
				})
			.ifPresent(existingSpeaker -> {
					throw new IllegalArgumentException("이미 예약된 확성기가 있습니다.");
				}
			);
	}

	private boolean checkIfReservationOverlaps(Speaker existingSpeaker, SpeakerRequestDto newSpeakerRequestDto) {
		LocalDateTime existingStartTime = existingSpeaker.getStartTime();
		LocalDateTime existingEndTime = existingSpeaker.getEndTime();

		LocalDateTime newStartTime = getLocalDateTime(newSpeakerRequestDto);
		LocalDateTime newEndTime = newStartTime.plusMinutes(30);

		// 예약 시간이 겹치는지 확인
		return existingStartTime.isBefore(newEndTime) && existingEndTime.isAfter(newStartTime);
	}

	private static LocalDateTime getLocalDateTime(SpeakerRequestDto speakerDto) {
		LocalDate reservationDate = speakerDto.getReservationDate();
		LocalTime reservationTime = speakerDto.getReservationTime();
		return LocalDateTime.of(reservationDate, reservationTime);
	}

	public List<SpeakerResponseDto> findSpeakerByLocalDate(String reservationDate) {
		LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(reservationDate), LocalTime.of(0, 0));
		LocalDateTime endDateTime = startDateTime.plusDays(1);
		List<Speaker> speakers = speakerRepository.findByReservationDate(startDateTime, endDateTime);
		return speakers.stream().map(SpeakerResponseDto::new).toList();
	}

	public List<SpeakerResponseDto> findAll() {
		List<Speaker> speakers = speakerRepository.findAll();
		return speakers.stream().map(SpeakerResponseDto::new).toList();
	}

	public SpeakerResponseDto findNowSpeaker() {
		Speaker speaker = speakerRepository.findCurrentSpeakerContent();
		return new SpeakerResponseDto(speaker);
	}
}
