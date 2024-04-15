package com.hanwha.solbangulrest.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.post.domain.Category;
import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.dto.PostSaveRequestDto;
import com.hanwha.solbangulrest.post.dto.PostUpdateDto;
import com.hanwha.solbangulrest.post.repository.PostRepository;
import com.hanwha.solbangulrest.post.specification.PostSpecification;
import com.hanwha.solbangulrest.room.domain.Room;
import com.hanwha.solbangulrest.room.repository.RoomRepository;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

	public static final int COMPLEMENT_FEE = 3;
	public static final int WRITE_FEE = 1;

	private final PostRepository postRepository;
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	@Transactional
	public Long save(PostSaveRequestDto postSaveRequestDto) {
		User author = userRepository.findById(postSaveRequestDto.getUserId()).orElseThrow(
			() -> new IllegalArgumentException("해당 user가 없습니다. id=" + postSaveRequestDto.getUserId()));

		Room room = roomRepository.findById(postSaveRequestDto.getRoomId()).orElseThrow(
			() -> new IllegalArgumentException("해당 room이 없습니다. id=" + postSaveRequestDto.getRoomId()));
		Post post = postSaveRequestDto.toEntity(author, room);

		Category category = post.getCategory();
		if (postRepository.existsByLastPost(author, room, category)) {
			postRepository.save(post);
			return post.getId();
		}
		calculateSolbangul(author, room, category);

		postRepository.save(post);
		return post.getId();
	}

	private static void calculateSolbangul(User author, Room room, Category category) {
		User roomUser = room.getUser();
		if (category.equals(Category.COMPLIMENT)) {
			roomUser.addSolbangul(COMPLEMENT_FEE);
		}
		author.addSolbangul(WRITE_FEE);
	}

	@Transactional
	public void update(Long id, PostUpdateDto postUpdateDto) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("해당 post가 없습니다. id=" + id));

		post.update(postUpdateDto.getTitle(), postUpdateDto.getContent(), postUpdateDto.getCategory(),
			postUpdateDto.getAnonymousYn());
	}

	public PostResponseDto findById(Long id) {
		Post post = postRepository.findByIdWithAuthorAndRoom(id).orElseThrow(
			() -> new IllegalArgumentException("해당 post가 없습니다. id=" + id));
		return new PostResponseDto(post);
	}

	@Transactional
	public void delete(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id " + id));
		Room room = post.getRoom();
		room.removePost(post);
		postRepository.delete(post);
	}

	@Transactional
	public Page<PostResponseDto> search(String keyword, String category, Long roomId, Pageable pageable) {

		Specification<Post> spec = Specification.where(PostSpecification.likeContents(keyword));
		spec = spec.or(PostSpecification.likeTitle(keyword));
		spec = spec.or(PostSpecification.likeAuthor(keyword));
		spec = spec.and(PostSpecification.findByRoomId(roomId));

		spec = switch (category) {
			case "compliment" -> spec.and(PostSpecification.findByCategory(Category.COMPLIMENT));
			case "claims" -> spec.and(PostSpecification.findByCategory(Category.CLAIMS));
			case "free" -> spec.and(PostSpecification.findByCategory(Category.FREE));
			default -> spec;
		};

		Page<Post> posts = postRepository.findAll(spec, pageable);

		return posts.map(PostResponseDto::new);
	}

	public Page<PostResponseDto> findPostsByRoomId(Long id, Pageable pageable) {
		Page<Post> posts = postRepository.findPostsByRoomIdPaging(id, pageable);
		return posts.map(PostResponseDto::new);
	}

	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Transactional
	public void updateViewCountById(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id " + id));
		post.viewCountUp();
	}
}
