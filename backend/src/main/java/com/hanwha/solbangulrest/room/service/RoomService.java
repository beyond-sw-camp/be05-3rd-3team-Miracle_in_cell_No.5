package com.hanwha.solbangulrest.room.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.room.domain.Room;
import com.hanwha.solbangulrest.room.dto.RoomResponseDto;
import com.hanwha.solbangulrest.room.dto.RoomUpdateDto;
import com.hanwha.solbangulrest.room.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

	private final RoomRepository roomRepository;

	public List<RoomResponseDto> findAll() {
		return roomRepository.findAllWithUser()
			.stream()
			.map(RoomResponseDto::new)
			.toList();
	}

	public RoomResponseDto findById(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));

		return new RoomResponseDto(room);
	}
	
	public List<PostResponseDto> findPostsByRoomId(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));

		List<Post> posts = room.getPosts();
		return posts.stream().map(PostResponseDto::new).toList();
	}

	@Transactional
	public void update(Long id, RoomUpdateDto roomUpdateDto) {
		Room room = roomRepository.findById(id).orElseThrow(()
			-> new IllegalArgumentException("해당 room이 없습니다. id=" + id));

		room.update(roomUpdateDto.getIntroduction(), roomUpdateDto.getRoomName());
	}
}
