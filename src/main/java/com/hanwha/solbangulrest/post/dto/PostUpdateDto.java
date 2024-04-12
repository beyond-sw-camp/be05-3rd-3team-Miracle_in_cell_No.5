package com.hanwha.solbangulrest.post.dto;

import com.hanwha.solbangulrest.post.domain.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateDto {

	private String title;
	private String content;
	private Category category;
	private Boolean anonymousYn;

	@Builder
	public PostUpdateDto(String title, String content, Category category, Boolean anonymousYn) {
		this.title = title;
		this.content = content;
		this.category = category;
		this.anonymousYn = anonymousYn;
	}
}
