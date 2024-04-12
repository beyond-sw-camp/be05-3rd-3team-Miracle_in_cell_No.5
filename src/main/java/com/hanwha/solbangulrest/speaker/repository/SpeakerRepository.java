package com.hanwha.solbangulrest.speaker.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanwha.solbangulrest.speaker.domain.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

	@Query("select s.content from Speaker s where NOW() between s.startTime and s.endTime")
	String findCurrentSpeakerContent();

	@Query("select s.startTime from Speaker s where NOW() < s.endTime")
	List<LocalDateTime> findReservedSpeakerTimes();
}
