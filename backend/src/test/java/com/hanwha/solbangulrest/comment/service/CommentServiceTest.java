package com.hanwha.solbangulrest.comment.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.comment.dto.CommentResponseDto;
import com.hanwha.solbangulrest.comment.dto.CommentSaveDto;
import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.hanwhauser.repository.HanwhaUserRepository;
import com.hanwha.solbangulrest.post.domain.Category;
import com.hanwha.solbangulrest.post.dto.PostSaveRequestDto;
import com.hanwha.solbangulrest.post.service.PostService;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;
import com.hanwha.solbangulrest.user.service.JoinService;

@Transactional
@SpringBootTest
class CommentServiceTest {

	public static final String HANWHA_USER_EMAIL_1 = "user1@gmail.com";
	public static final String HANWHA_USER_EMAIL_2 = "user2@gmail.com";
	public static final String USER_NAME = "test";

	@Autowired
	private PostService postService;
	@Autowired
	private JoinService joinService;
	@Autowired
	private HanwhaUserRepository hanwhaUserRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentService commentService;

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
	@DisplayName("댓글 저장")
	void save() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.CLAIMS);
		Long postId = postService.save(postSaveRequestDto);

		CommentSaveDto commentSaveDto = CommentSaveDto.builder()
			.postId(postId)
			.loginId(loginId)
			.content("test")
			.build();

		// when
		commentService.save(commentSaveDto);
		commentService.save(commentSaveDto);
		commentService.save(commentSaveDto);

		List<CommentResponseDto> comments = commentService.findByPostId(postId);
		CommentResponseDto comment = comments.get(0);

		// then
		assertThat(comments.size()).isEqualTo(3);
		assertThat(comment.getContent()).isEqualTo("test");
		assertThat(comment.getNickname()).isEqualTo("보내는 사람");
		assertThat(comment.getProfileImage()).isEqualTo("default.png");
	}

	@Test
	@DisplayName("댓글 삭제")
	void delete() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.CLAIMS);
		Long postId = postService.save(postSaveRequestDto);

		CommentSaveDto commentSaveDto = CommentSaveDto.builder()
			.postId(postId)
			.loginId(loginId)
			.content("test")
			.build();

		// when
		Long commentId = commentService.save(commentSaveDto);
		commentService.save(commentSaveDto);
		commentService.save(commentSaveDto);

		List<CommentResponseDto> comments = commentService.findByPostId(postId);
		assertThat(comments.size()).isEqualTo(3);

		// then
		commentService.delete(commentId);
		assertThat(commentService.findByPostId(postId).size()).isEqualTo(2);
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