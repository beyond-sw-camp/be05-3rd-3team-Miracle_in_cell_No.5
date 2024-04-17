package com.hanwha.solbangulrest.comment.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.comment.dto.CommentResponseDto;
import com.hanwha.solbangulrest.comment.dto.CommentSaveDto;
import com.hanwha.solbangulrest.comment.service.CommentService;
import com.hanwha.solbangulrest.global.common.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/{postId}")
	public Result<List<CommentResponseDto>> findCommentsByPost(@PathVariable Long postId) {
		List<CommentResponseDto> comments = commentService.findByPostId(postId);
		return new Result<>(true, "댓글 목록 조회", comments);
	}

	@PostMapping("/{postId}")
	public Result<Void> saveComment(@PathVariable Long postId, @RequestBody @Valid CommentSaveDto commentDto) {
		commentDto.setPostId(postId);
		commentDto.setLoginId(SecurityContextHolder.getContext().getAuthentication().getName());

		commentService.save(commentDto);
		return new Result<>(true, "댓글 저장 완료", null);
	}

	@DeleteMapping("/{commentId}")
	public Result<Void> deleteComment(@PathVariable Long commentId) {
		commentService.delete(commentId);
		return new Result<>(true, "댓글 삭제 완료", null);
	}
}
