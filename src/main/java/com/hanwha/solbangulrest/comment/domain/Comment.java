package com.hanwha.solbangulrest.comment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.hanwha.solbangulrest.BaseTimeEntity;
import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "comment_content")
	private String content;

	@Column(name = "comment_writer")
	private String writer;

	@Builder
	public Comment(Post post, User user, String content, String writer) {
		this.post = post;
		this.user = user;
		this.content = content;
		this.writer = writer;
	}

	public void update(String content) {
		this.content = content;
	}
}
