package com.hanwha.solbangulrest.post.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.dto.PostSaveRequestDto;
import com.hanwha.solbangulrest.post.dto.PostUpdateDto;
import com.hanwha.solbangulrest.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

	private final PostService postService;

	@PostMapping("/{roomId}")
	public Result<Void> save(@PathVariable Long roomId, @RequestBody @Valid PostSaveRequestDto postDto) {
		postDto.setLoginId(SecurityContextHolder.getContext().getAuthentication().getName());
		postDto.setRoomId(roomId);

		postService.save(postDto);
		return new Result<>(true, "게시글이 등록되었습니다.", null);
	}

	@PatchMapping("/{postId}")
	public Result<Void> update(@PathVariable Long postId, @RequestBody @Valid PostUpdateDto postDto) {
		postService.update(postId, postDto);
		return new Result<>(true, "게시글이 수정되었습니다.", null);
	}

	@DeleteMapping("/{postId}")
	public Result<Void> delete(@PathVariable Long postId) {
		postService.delete(postId);
		return new Result<>(true, "게시글이 삭제되었습니다.", null);
	}

	@GetMapping("/{postId}")
	public Result<PostResponseDto> view(@PathVariable Long postId, HttpServletRequest request,
		HttpServletResponse response) {
		PostResponseDto postResponseDto = postService.findById(postId);
		viewCountUp(postId, request, response);
		return new Result<>(true, postId + "번 글 조회", postResponseDto);
	}

	private void viewCountUp(Long id, HttpServletRequest request, HttpServletResponse response) {
		Cookie oldCookie = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("viewCount")) {
					oldCookie = cookie;
				}
			}
		}

		if (oldCookie != null) {
			if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
				postService.updateViewCountById(id);
				oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 10);
				response.addCookie(oldCookie);
			}
		} else {
			postService.updateViewCountById(id);
			Cookie newCookie = new Cookie("viewCount", "[" + id + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 10);
			response.addCookie(newCookie);
		}
	}
}
