package com.hanwha.solbangulrest.room.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.room.dto.RoomResponseDto;
import com.hanwha.solbangulrest.room.dto.RoomUpdateDto;
import com.hanwha.solbangulrest.room.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {
	@Autowired
	private RoomService roomService ;

	@GetMapping("/list")
	public ResponseEntity<?> list(){
		List<RoomResponseDto> list = roomService.findAll() ;
		return  ResponseEntity.ok(list);
	}


	@GetMapping("/{roomId}/view")
	public ResponseEntity<RoomResponseDto> viewRoom(@PathVariable(name = "roomId") Long id) {
		RoomResponseDto response = roomService.findById(id);

		return new ResponseEntity<RoomResponseDto>(response,HttpStatus.OK) ;
	}

	@PutMapping("/{roomId}/edit")
	public ResponseEntity<Void> editRoom(@PathVariable(name = "roomId") Long id,@RequestBody RoomUpdateDto roomDto){
		roomService.update(id, roomDto) ;
		return new ResponseEntity<Void> (HttpStatus.NO_CONTENT) ;
	}
}
