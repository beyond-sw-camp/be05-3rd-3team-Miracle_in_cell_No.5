package com.hanwha.solbangulrest.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.user.domain.Role;
import com.hanwha.solbangulrest.user.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinUserDto {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String loginId;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
		message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
	private String password;

	@NotBlank(message = "비밀번호 확인을 입력해주세요")
	private String passwordConfirm;
	
	private String username;

	@NotBlank(message = "닉네임을 입력해주세요")
	private String nickname;

	@Email
	@NotBlank(message = "이메일을 입력해주세요")
	private String gitEmail;

	private MultipartFile multipartFile;
	private String profileImage;

	public void setEncodedPassword(String encodePassword) {
		this.password = encodePassword;
	}

	@Builder
	public JoinUserDto(String loginId, String password, String passwordConfirm, String username, String nickname,
		String gitEmail, MultipartFile multipartFile, String profileImage) {
		this.loginId = loginId;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
		this.username = username;
		this.nickname = nickname;
		this.gitEmail = gitEmail;
		this.multipartFile = multipartFile;
		this.profileImage = profileImage;
	}

	public User toEntity(HanwhaUser hanwhaUser) {
		return User.builder()
			.loginId(loginId)
			.password(password)
			.hanwhaUser(hanwhaUser)
			.nickname(nickname)
			.profileImage(profileImage)
			.role(Role.ROLE_USER)
			.build();
	}
}
