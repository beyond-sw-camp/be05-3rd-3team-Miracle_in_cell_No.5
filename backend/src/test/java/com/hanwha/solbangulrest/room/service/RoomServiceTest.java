package com.hanwha.solbangulrest.room.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.hanwhauser.repository.HanwhaUserRepository;
import com.hanwha.solbangulrest.post.domain.Category;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.dto.PostSaveRequestDto;
import com.hanwha.solbangulrest.post.service.PostService;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;
import com.hanwha.solbangulrest.user.service.JoinService;

@Transactional
@SpringBootTest
class RoomServiceTest {
	public static final String HANWHA_USER_EMAIL_1 = "user1@gmail.com";
	public static final String HANWHA_USER_EMAIL_2 = "user2@gmail.com";
	public static final String USER_NAME = "test";

	@Autowired
	private PostService postService;

	@Autowired
	private JoinService joinService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private HanwhaUserRepository hanwhaUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager em;

	@BeforeEach
	void init() {
		// fixture
		HanwhaUser savedHanwhaUser1 = HanwhaUser
			.builder()
			.username(USER_NAME)
			.gitEmail(HANWHA_USER_EMAIL_1)
			.isMember(false)
			.build();
		hanwhaUserRepository.save(savedHanwhaUser1);

		HanwhaUser savedHanwhaUser2 = HanwhaUser
			.builder()
			.username(USER_NAME)
			.gitEmail(HANWHA_USER_EMAIL_2)
			.isMember(false)
			.build();
		hanwhaUserRepository.save(savedHanwhaUser2);

		joinService.join(JoinUserDto.builder()
			.loginId("test1")
			.nickname("보내는 사람")
			.gitEmail(HANWHA_USER_EMAIL_1)
			.password("password1")
			.passwordConfirm("password1")
			.build());

		joinService.join(JoinUserDto.builder()
			.loginId("test2")
			.nickname("받는 사람")
			.gitEmail(HANWHA_USER_EMAIL_2)
			.password("password2")
			.passwordConfirm("password2")
			.build());
	}

	@Test
	@DisplayName("방에 게시글 삭제된 경우 방이 가지고 있는 게시글 수가 감소한다.")
	void deletePost() {
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.CLAIMS);
		postService.save(postSaveRequestDto);
		postService.save(postSaveRequestDto);
		postService.save(postSaveRequestDto);
		postService.save(postSaveRequestDto);
		Long postId = postService.save(postSaveRequestDto);

		List<PostResponseDto> posts = roomService.findPostsByRoomId(roomId);
		assertThat(posts.size()).isEqualTo(5);

		postService.delete(postId);
		assertThat(roomService.findPostsByRoomId(roomId).size()).isEqualTo(4);
	}

	private static PostSaveRequestDto getPostSaveRequestDto(String loginId, Long roomId, Category category) {
		return PostSaveRequestDto.builder()
			.loginId(loginId)
			.roomId(roomId)
			.title("title")
			.content("content")
			.anonymousYn(true)
			.category(category)
			.build();
	}

	private Result getUserIdAndRoomId() {
		List<User> users = userRepository.findAll();
		String loginId = users.get(0).getLoginId();
		Long roomId = users.get(1).getRoom().getId();
		return new Result(loginId, roomId);
	}

	private record Result(String loginId, Long roomId) {
	}
}