package com.hanwha.solbangulrest.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {

	private String title;
	private String content;
	private Boolean anonymousYn;

	@Builder
	public PostUpdateDto(String title, String content, Boolean anonymousYn) {
		this.title = title;
		this.content = content;
		this.anonymousYn = anonymousYn;
	}
}
