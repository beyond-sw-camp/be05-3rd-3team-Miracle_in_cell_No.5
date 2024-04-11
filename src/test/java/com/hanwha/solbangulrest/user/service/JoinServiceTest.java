package com.hanwha.solbangulrest.user.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.hanwhauser.repository.HanwhaUserRepository;
import com.hanwha.solbangulrest.room.domain.Room;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;

@Transactional
@SpringBootTest
class JoinServiceTest {

	public static final String HANWHA_USER_EMAIL_1 = "user1@gmail.com";
	public static final String HANWHA_USER_EMAIL_2 = "user2@gmail.com";
	public static final String USER_NAME = "test";

	@Autowired
	private JoinService joinService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HanwhaUserRepository hanwhaUserRepository;

	@BeforeEach
	void setUp() {
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
	}

	@Test
	@DisplayName("한화 교육생이 아닌 유저가 회원가입 하는 경우 예외 발생")
	void join_exception() throws Exception {
		// given
		String nonHanwhaUserEmail = "notsaved@gmail.com";

		// when
		JoinUserDto joinUserDto = getJoinUserDto("testId", "testPassword1!",
			"testPassword1!", nonHanwhaUserEmail, "테스트 닉네임");

		// then
		assertThatThrownBy(() -> joinService.join(joinUserDto))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("한화 SW교육 5기생만 가입 가능합니다.");
	}

	@Test
	@DisplayName("이미 가입된 이메일 회원가입 하는 경우 예외 발생")
	void join_already_email() throws Exception {
		// given
		JoinUserDto user1 = getJoinUserDto("아이디1", "testPassword1!",
			"testPassword1!", HANWHA_USER_EMAIL_1, "닉네임1");
		joinService.join(user1);

		// when
		JoinUserDto user2 = getJoinUserDto("아이디2", "testPassword1!",
			"testPassword1!", HANWHA_USER_EMAIL_1, "닉네임2");

		// then
		assertThatThrownBy(() -> joinService.join(user2))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이미 가입된 이메일입니다.");
	}

	@Test
	@DisplayName("중복된 아이디로 회원가입 하는 경우 예외 발생")
	void join_exception_duplicate_loginId() throws Exception {
		// given
		String duplicateLoginId = "duplicateLoginId";

		JoinUserDto user1 = getJoinUserDto(duplicateLoginId, "testPassword1!", "testPassword1!",
			HANWHA_USER_EMAIL_1, "닉네임1");

		JoinUserDto user2 = getJoinUserDto(duplicateLoginId, "testPassword1!", "testPassword1!",
			HANWHA_USER_EMAIL_2, "닉네임2");

		// when
		joinService.join(user1);

		// then
		assertThatThrownBy(() -> joinService.join(user2))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이미 존재하는 아이디입니다.");
	}

	@Test
	@DisplayName("중복된 닉네임으로 회원가입 하는 경우 예외 발생")
	void join_exception_duplicate_nickname() throws Exception {
		// given
		String duplicateNickname = "duplicateNickname";

		JoinUserDto user1 = getJoinUserDto("아이디1", "testPassword1!", "testPassword1!",
			HANWHA_USER_EMAIL_1, duplicateNickname);

		JoinUserDto user2 = getJoinUserDto("아이디2", "testPassword1!", "testPassword1!",
			HANWHA_USER_EMAIL_2, duplicateNickname);

		// when
		joinService.join(user1);

		// then
		assertThatThrownBy(() -> joinService.join(user2))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("이미 존재하는 닉네임입니다.");
	}

	@Test
	@DisplayName("비밀번호가 일치하지 않는 경우 예외 발생")
	void join_exception_password_not_match() throws Exception {
		// given
		JoinUserDto user = getJoinUserDto("아이디1", "testPassword1!", "testPassword2!",
			HANWHA_USER_EMAIL_1, "닉네임1");

		// then
		assertThatThrownBy(() -> joinService.join(user))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("비밀번호가 일치하지 않습니다.");
	}

	@Test
	@DisplayName("정상적으로 회원가입 되는 경우")
	void join() throws Exception {
		// given
		String loginId = "아이디1";
		String nickname = "닉네임1";
		JoinUserDto user = getJoinUserDto(loginId, "testPassword1!", "testPassword1!",
			HANWHA_USER_EMAIL_1, nickname);

		// when
		Long id = joinService.join(user);
		User findUser = userRepository.findById(id).orElseThrow();
		HanwhaUser findHanwhaUser = findUser.getHanwhaUser();
		Room findRoom = findUser.getRoom();

		// then
		assertThat(userRepository.existsByLoginId(loginId)).isTrue();
		assertThat(userRepository.existsByNickname(nickname)).isTrue();
		assertThat(findUser.getLoginId()).isEqualTo(loginId);
		assertThat(findUser.getNickname()).isEqualTo(nickname);
		assertThat(findUser.getSolbangul()).isEqualTo(0);
		assertThat(findHanwhaUser.getGitEmail()).isEqualTo(HANWHA_USER_EMAIL_1);
		assertThat(findHanwhaUser.getUsername()).isEqualTo(USER_NAME);
		assertThat(findHanwhaUser.getIsMember()).isTrue();
		assertThat(findRoom.getUser()).isEqualTo(findUser);
		assertThat(findRoom.getRoomName()).isEqualTo(nickname + "의 방");
		assertThat(findRoom.getIntroduction()).isEqualTo("안녕하세요! " + nickname + "의 방입니다.");
	}

	private static JoinUserDto getJoinUserDto(String loginId, String password, String passwordConfirm, String gitEmail,
		String nickname) {
		return JoinUserDto.builder()
			.loginId(loginId)
			.password(password)
			.passwordConfirm(passwordConfirm)
			.username(USER_NAME)
			.nickname(nickname)
			.gitEmail(gitEmail)
			.multipartFile(null)
			.profileImage(null)
			.build();
	}

}