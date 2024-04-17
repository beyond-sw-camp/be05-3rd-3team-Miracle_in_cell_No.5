package com.hanwha.solbangulrest.post.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {

	@NotBlank(message = "제목을 입력해주세요")
	private String title;

	@NotBlank(message = "내용을 입력해주세요")
	private String content;
	private Boolean anonymousYn;

	@Builder
	public PostUpdateDto(String title, String content, Boolean anonymousYn) {
		this.title = title;
		this.content = content;
		this.anonymousYn = anonymousYn;
	}
}
