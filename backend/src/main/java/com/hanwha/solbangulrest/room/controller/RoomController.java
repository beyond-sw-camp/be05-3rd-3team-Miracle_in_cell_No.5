package com.hanwha.solbangulrest.room.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
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

	@GetMapping("/list")
	public Result<List<RoomResponseDto>> list() {
		List<RoomResponseDto> list = roomService.findAll();
		return new Result<>(true, "방 목록 조회", list);
	}

	@GetMapping("/{roomId}/view")
	public ResponseEntity<RoomResponseDto> viewRoom(@PathVariable(name = "roomId") Long id) {
		RoomResponseDto response = roomService.findById(id);

		return new ResponseEntity<RoomResponseDto>(response, HttpStatus.OK);
	}

	@PatchMapping("/{roomId}/edit")
	public ResponseEntity<Void> editRoom(@PathVariable(name = "roomId") Long id, @RequestBody RoomUpdateDto roomDto) {
		roomService.update(id, roomDto);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
