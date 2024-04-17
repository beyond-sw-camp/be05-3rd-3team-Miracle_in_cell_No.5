package com.hanwha.solbangulrest.room.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.service.PostService;
import com.hanwha.solbangulrest.room.dto.RoomResponseDto;
import com.hanwha.solbangulrest.room.dto.RoomUpdateDto;
import com.hanwha.solbangulrest.room.service.RoomService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {

	private final RoomService roomService;
	private final PostService postService;

	@GetMapping
	public Result<List<RoomResponseDto>> list() {
		List<RoomResponseDto> list = roomService.findAll();
		return new Result<>(true, "방 목록 조회", list);
	}

	@GetMapping("/{roomId}")
	public Result<RoomResponseDto> viewRoom(@PathVariable Long roomId) {
		RoomResponseDto response = roomService.findById(roomId);
		return new Result<>(true, "방 정보 조회", response);
	}

	@PatchMapping("/{roomId}")
	public Result<Void> editRoom(@PathVariable Long roomId, @RequestBody RoomUpdateDto roomDto) {
		roomService.update(roomId, roomDto);
		return new Result<>(true, "방 정보 수정 완료", null);
	}

	@GetMapping("/{roomId}/posts")
	public Result<Page<PostResponseDto>> viewPosts(@PathVariable Long roomId,
		@RequestParam(value = "keyword", defaultValue = "") String keyword,
		@PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<PostResponseDto> posts = postService.findPostsByRoomId(roomId, pageable);
		return new Result<>(true, "방 글 목록 조회", posts);
	}

	@GetMapping("/{roomId}/search")
	public Result<Page<PostResponseDto>> searchPosts(@PathVariable Long roomId,
		@RequestParam(value = "keyword", defaultValue = "") String keyword,
		@RequestParam(value = "category", defaultValue = "") String category,
		@PageableDefault(size = 10, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

		Page<PostResponseDto> posts = postService.search(keyword, category, roomId, pageable);
		return new Result<>(true, "방 글 검색 결과", posts);
	}
}
