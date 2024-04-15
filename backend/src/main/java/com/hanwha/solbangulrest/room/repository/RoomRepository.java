package com.hanwha.solbangulrest.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hanwha.solbangulrest.room.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query("select r from Room r join fetch r.user")
	List<Room> findAllWithUser();
}
