package com.hanwha.solbangulrest.post.dto;

import java.time.LocalDateTime;

import com.hanwha.solbangulrest.post.domain.Category;
import com.hanwha.solbangulrest.post.domain.Post;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostResponseDto {

	private Long id;
	private String title;
	private String content;
	private String authorNickname;
	private String roomUserNickname;
	private Category category;
	private Integer viewCount;
	private Boolean anonymousYn;
	private String authorProfileImage;
	private LocalDateTime createdDateTime;
	private LocalDateTime lastModifiedDateTime;

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.authorNickname = post.getAuthor().getNickname();
		this.roomUserNickname = post.getRoom().getUser().getNickname();
		this.category = post.getCategory();
		this.viewCount = post.getViewCount();
		this.anonymousYn = post.getAnonymousYn();
		this.authorProfileImage = post.getAuthor().getProfileImage();
		this.createdDateTime = post.getCreatedDateTime();
		this.lastModifiedDateTime = post.getLastModifiedDateTime();
	}
}
