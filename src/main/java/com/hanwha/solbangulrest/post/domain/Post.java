package com.hanwha.solbangulrest.post.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import com.hanwha.solbangulrest.BaseTimeEntity;
import com.hanwha.solbangulrest.comment.domain.Comment;
import com.hanwha.solbangulrest.user.domain.User;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "post_title")
	private String title;

	@Column(name = "post_content")
	private String content;

	@Column(name = "post_writer")
	private String writer;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private Category category;

	private Boolean anonymousYn = false;
	private Integer viewCount = 0;
	private Integer likesCount = 0;

	@Builder
	public Post(User user, String title, String content, String writer, Category category, Boolean anonymousYn,
		Integer viewCount, Integer likesCount) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.category = category;
		this.anonymousYn = anonymousYn;
		this.viewCount = viewCount;
		this.likesCount = likesCount;
	}

	public void update(String title, String content, String writer, Category category, Boolean anonymousYn) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.category = category;
		this.anonymousYn = anonymousYn;
	}

	// 연관관계 편의 메서드
	public void addComment(Comment comment) {
		comment.setPost(this);
		comments.add(comment);
	}
}
