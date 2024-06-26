package com.hanwha.solbangulrest.post.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		User author = userRepository.findByLoginId(postSaveRequestDto.getLoginId()).orElseThrow(
			() -> new IllegalArgumentException("해당 user가 없습니다."));

		Room room = roomRepository.findById(postSaveRequestDto.getRoomId()).orElseThrow(
			() -> new IllegalArgumentException("해당 room이 없습니다."));
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
			() -> new IllegalArgumentException("해당 post가 없습니다."));

		post.update(postUpdateDto.getTitle(), postUpdateDto.getContent(), postUpdateDto.getAnonymousYn());
	}

	public PostResponseDto findById(Long id) {
		Post post = postRepository.findByIdWithAuthorAndRoom(id).orElseThrow(
			() -> new IllegalArgumentException("해당 post가 없습니다."));
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

	// @Transactional
	// public Page<PostResponseDto> search(String keyword, String category, Long roomId, Pageable pageable) {
	//
	// 	Specification<Post> spec = Specification.where(PostSpecification.likeContents(keyword));
	// 	spec = spec.or(PostSpecification.likeTitle(keyword));
	// 	spec = spec.or(PostSpecification.likeAuthor(keyword));
	// 	spec = spec.and(PostSpecification.findByRoomId(roomId));
	//
	// 	spec = switch (category) {
	// 		case "COMPLEMENT" -> spec.and(PostSpecification.findByCategory(Category.COMPLIMENT));
	// 		case "CLAIMS" -> spec.and(PostSpecification.findByCategory(Category.CLAIMS));
	// 		case "FREE" -> spec.and(PostSpecification.findByCategory(Category.FREE));
	// 		default -> spec;
	// 	};
	//
	// 	Page<Post> posts = postRepository.findAll(spec, pageable);
	//
	// 	return posts.map(PostResponseDto::new);
	// }
	@Transactional(readOnly = true)
	public Page<PostResponseDto> search(String keyword, String category, Long roomId, int pageNumber, Pageable pageable) {
		Specification<Post> spec = Specification.where(PostSpecification.likeContents(keyword))
			.or(PostSpecification.likeTitle(keyword))
			.or(PostSpecification.likeAuthor(keyword))
			.and(PostSpecification.findByRoomId(roomId));

		switch (category) {
			case "COMPLEMENT":
				spec = spec.and(PostSpecification.findByCategory(Category.COMPLIMENT));
				break;
			case "CLAIMS":
				spec = spec.and(PostSpecification.findByCategory(Category.CLAIMS));
				break;
			case "FREE":
				spec = spec.and(PostSpecification.findByCategory(Category.FREE));
				break;
			default:
				break;
		}

		Page<Post> posts = postRepository.findAll(spec, PageRequest.of(pageNumber-1, pageable.getPageSize(), pageable.getSort()));

		return posts.map(PostResponseDto::new);
	}

	public Page<PostResponseDto> findPostsByRoomId(Long id, Pageable pageable) {
		Page<Post> posts = postRepository.findPostsByRoomIdPaging(id, pageable);
		return posts.map(PostResponseDto::new);
	}

	public List<Post> findAllForTest() {
		return postRepository.findAll();
	}

	public Page<PostResponseDto> findAll(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);
		return posts.map(PostResponseDto::new);
	}

	@Transactional
	public void updateViewCountById(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id " + id));
		post.viewCountUp();
	}
}
