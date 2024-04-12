package com.hanwha.solbangulrest.comment.dto;

import java.time.LocalDateTime;

import com.hanwha.solbangulrest.comment.domain.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {

	private String content;
	private String nickname;
	private String profileImage;
	private LocalDateTime createdDate;

	public CommentResponseDto(Comment comment) {
		this.content = comment.getContent();
		this.nickname = comment.getAuthor().getNickname();
		this.profileImage = comment.getAuthor().getProfileImage();
		this.createdDate = comment.getCreatedDate();
	}
}
