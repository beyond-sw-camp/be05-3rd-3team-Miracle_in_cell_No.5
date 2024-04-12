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

	private String title;
	private String content;
	private String authorNickname;
	private String roomUserNickname;
	private Category category;
	private Integer viewCount;
	private Boolean anonymousYn;
	private String authorProfileImage;
	private LocalDateTime createDate;
	private LocalDateTime lastModifiedDate;

	public PostResponseDto(Post post) {
		this.title = post.getTitle();
		this.content = post.getContent();
		this.authorNickname = post.getAuthor().getNickname();
		this.roomUserNickname = post.getRoom().getUser().getNickname();
		this.category = post.getCategory();
		this.viewCount = post.getViewCount();
		this.anonymousYn = post.getAnonymousYn();
		this.authorProfileImage = post.getAuthor().getProfileImage();
		this.createDate = post.getCreatedDate();
		this.lastModifiedDate = post.getLastModifiedDate();
	}
}
