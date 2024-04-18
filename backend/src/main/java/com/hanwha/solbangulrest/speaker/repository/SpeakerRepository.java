package com.hanwha.solbangulrest.speaker.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hanwha.solbangulrest.speaker.domain.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

	@Query("select s from Speaker s where NOW() between s.startTime and s.endTime")
	Optional<Speaker> findCurrentSpeakerContent();

	@Query("select s.startTime from Speaker s where NOW() < s.endTime")
	List<LocalDateTime> findReservedSpeakerTimes();

	@Query("select s from Speaker s join fetch s.user where s.startTime >= :reservationDate and s.startTime < :nextDay order by s.startTime asc")
	List<Speaker> findByReservationDate(@Param("reservationDate") LocalDateTime reservationDate,
		@Param("nextDay") LocalDateTime nextDay);

}
