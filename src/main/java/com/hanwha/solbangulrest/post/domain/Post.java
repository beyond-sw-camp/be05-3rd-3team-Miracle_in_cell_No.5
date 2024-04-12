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
import com.hanwha.solbangulrest.room.domain.Room;
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
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@Column(name = "post_title")
	private String title;

	@Column(name = "post_content")
	private String content;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private Category category;

	private Boolean anonymousYn;
	private Integer viewCount;
	private Integer likesCount;

	@Builder
	public Post(User author, Room room, String title, String content, Category category, Boolean anonymousYn) {
		this.author = author;
		addRoom(room);
		this.title = title;
		this.content = content;
		this.category = category;
		this.anonymousYn = anonymousYn;
		this.viewCount = 0;
		this.likesCount = 0;
	}

	public void update(String title, String content, Category category, Boolean anonymousYn) {
		this.title = title;
		this.content = content;
		this.category = category;
		this.anonymousYn = anonymousYn;
	}

	// 연관관계 편의 메서드
	public void addComment(Comment comment) {
		comment.setPost(this);
		comments.add(comment);
	}

	// 연관관계 편의 메서드
	public void addRoom(Room room) {
		this.room = room;
		room.getPosts().add(this);
	}

	public void removeComment(Comment comment) {
		this.comments.remove(comment);
	}

	public void viewCountUp() {
		this.viewCount++;
	}
}
