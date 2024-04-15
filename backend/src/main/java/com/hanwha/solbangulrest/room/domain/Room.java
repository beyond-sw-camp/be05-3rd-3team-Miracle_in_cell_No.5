package com.hanwha.solbangulrest.room.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

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
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;

	@Setter
	@OneToOne(mappedBy = "room")
	private User user;

	@OneToMany(mappedBy = "room")
	private List<Post> posts = new ArrayList<>();

	private String roomName;
	private String introduction;

	@Builder
	public Room(String roomName, String introduction) {
		this.roomName = roomName;
		this.introduction = introduction;
	}

	public void update(String roomName, String introduction) {
		this.roomName = roomName;
		this.introduction = introduction;
	}

	// 연관관계 편의 메서드
	public void removePost(Post post) {
		this.posts.remove(post);
	}
}
