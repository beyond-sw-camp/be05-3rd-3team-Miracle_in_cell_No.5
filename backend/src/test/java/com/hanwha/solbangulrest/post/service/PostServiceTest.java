package com.hanwha.solbangulrest.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.hanwhauser.repository.HanwhaUserRepository;
import com.hanwha.solbangulrest.post.domain.Category;
import com.hanwha.solbangulrest.post.domain.Post;
import com.hanwha.solbangulrest.post.dto.PostResponseDto;
import com.hanwha.solbangulrest.post.dto.PostSaveRequestDto;
import com.hanwha.solbangulrest.post.dto.PostUpdateDto;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;
import com.hanwha.solbangulrest.user.service.JoinService;

@Transactional
@SpringBootTest
class PostServiceTest {

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
	@DisplayName("게시글 저장")
	void save() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.COMPLIMENT);

		// when
		postService.save(postSaveRequestDto); // author + 1, roomUser + 3

		List<Post> posts = postService.findAllForTest();
		Post post = posts.get(0);
		User author = post.getAuthor();
		User roomUser = post.getRoom().getUser();

		// then
		assertThat(posts.size()).isEqualTo(1);
		assertThat(post.getTitle()).isEqualTo("title");
		assertThat(post.getContent()).isEqualTo("content");
		assertThat(post.getAnonymousYn()).isTrue();
		assertThat(post.getCategory()).isEqualTo(Category.COMPLIMENT);

		assertThat(author.getSolbangul()).isEqualTo(1);
		assertThat(roomUser.getSolbangul()).isEqualTo(3);
	}

	@Test
	@DisplayName("같은 사람이 같은 방에 같은 카테고리를 같은 날 작성한 경우 솔방울 오르지 않음")
	void existLastPost() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.FREE);

		// when
		postService.save(postSaveRequestDto); // author + 1
		postService.save(postSaveRequestDto);

		List<Post> posts = postService.findAllForTest();
		Post post = posts.get(0);

		User author = post.getAuthor();
		User roomUser = post.getRoom().getUser();

		// then
		assertThat(author.getSolbangul()).isEqualTo(1);
		assertThat(roomUser.getSolbangul()).isEqualTo(0);
	}

	@Test
	@DisplayName("같은 사람이 같은 방에 다른 카테고리를 같은 날 작성한 경우 솔방울은 오름")
	void existLastPostAndAnotherCategory() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.COMPLIMENT);

		// when
		postService.save(postSaveRequestDto); // author + 1, roomUser + 3

		postSaveRequestDto.setCategory(Category.CLAIMS);
		postService.save(postSaveRequestDto); // author + 1

		List<Post> posts = postService.findAllForTest();
		Post post = posts.get(0);

		User author = post.getAuthor();
		User roomUser = post.getRoom().getUser();

		// then
		assertThat(author.getSolbangul()).isEqualTo(2);
		assertThat(roomUser.getSolbangul()).isEqualTo(3);
	}

	@Test
	@DisplayName("게시글 수정")
	void update() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.COMPLIMENT);

		// when
		postService.save(postSaveRequestDto);

		List<Post> posts = postService.findAllForTest();
		Post post = posts.get(0);

		PostUpdateDto postUpdateDto = PostUpdateDto.builder()
			.title("update title")
			.content("update content")
			.anonymousYn(false)
			.build();
		postService.update(post.getId(), postUpdateDto);
		PostResponseDto postResponseDto = postService.findById(post.getId());

		// then
		assertThat(postResponseDto.getTitle()).isEqualTo("update title");
		assertThat(postResponseDto.getContent()).isEqualTo("update content");
		assertThat(postResponseDto.getAnonymousYn()).isFalse();
	}

	@Test
	@DisplayName("게시글 삭제")
	void delete() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.COMPLIMENT);

		// when
		postService.save(postSaveRequestDto);
		postService.save(postSaveRequestDto);
		postService.save(postSaveRequestDto);
		assertThat(postService.findAllForTest().size()).isEqualTo(3);

		List<Post> posts = postService.findAllForTest();
		Post post = posts.get(0);
		postService.delete(post.getId());

		// then
		assertThat(postService.findAllForTest().size()).isEqualTo(2);
	}

	@Test
	@DisplayName("게시글 검색")
	void search() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto1 = getPostSaveRequestDto(loginId, roomId, Category.COMPLIMENT,
			"유저1을 칭찬합니다.", "compliment content");
		PostSaveRequestDto postSaveRequestDto2 = getPostSaveRequestDto(loginId, roomId, Category.CLAIMS,
			"claims", "claims content");
		PostSaveRequestDto postSaveRequestDto3 = getPostSaveRequestDto(loginId, roomId, Category.FREE,
			"free", "free content");

		// when
		for (int i = 0; i < 30; i++) {
			postService.save(postSaveRequestDto1);
			postService.save(postSaveRequestDto2);
			postService.save(postSaveRequestDto3);
		}

		// then
		PageRequest pageable = PageRequest.of(0, 10);
		Page<PostResponseDto> postPage = postService.search("", Category.COMPLIMENT.toString(), roomId, 1,pageable);

		List<PostResponseDto> posts = postPage.getContent();
		assertThat(posts.size()).isEqualTo(10);
		assertThat(posts.get(0).getTitle()).isEqualTo("유저1을 칭찬합니다.");
		assertThat(posts.get(0).getContent()).isEqualTo("compliment content");
	}

	@Test
	@DisplayName("게시글 조회수 증가")
	void viewCount() throws Exception {
		// given
		Result result = getUserIdAndRoomId();
		String loginId = result.loginId();
		Long roomId = result.roomId();

		PostSaveRequestDto postSaveRequestDto = getPostSaveRequestDto(loginId, roomId, Category.COMPLIMENT);
		postService.save(postSaveRequestDto);

		// when
		List<Post> posts = postService.findAllForTest();
		Post post = posts.get(0);
		assertThat(post.getViewCount()).isEqualTo(0);

		// then
		postService.updateViewCountById(post.getId());
		postService.updateViewCountById(post.getId());
		assertThat(post.getViewCount()).isEqualTo(2);
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

	private static PostSaveRequestDto getPostSaveRequestDto(String loginId, Long roomId, Category category,
		String title, String content) {
		return PostSaveRequestDto.builder()
			.loginId(loginId)
			.roomId(roomId)
			.title(title)
			.content(content)
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