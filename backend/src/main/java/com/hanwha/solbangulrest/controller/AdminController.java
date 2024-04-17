package com.hanwha.solbangulrest.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

	private final PostService postService;

	@GetMapping
	public Result<Page<PostResponseDto>> viewAllPosts(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
		return new Result<>(true, "게시글 목록 조회", postService.findAll(pageable));
	}

	@DeleteMapping("/{postId}")
	public Result<Void> deleteByAdmin(@PathVariable Long postId) {
		postService.delete(postId);
		return new Result<>(true, "어드민 권한 게시글 삭제 완료", null);
	}
}
