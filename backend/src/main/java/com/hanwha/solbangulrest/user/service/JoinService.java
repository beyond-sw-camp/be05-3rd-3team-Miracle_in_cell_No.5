package com.hanwha.solbangulrest.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanwha.solbangulrest.file.FileStore;
import com.hanwha.solbangulrest.file.UploadFile;
import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.hanwhauser.repository.HanwhaUserRepository;
import com.hanwha.solbangulrest.room.domain.Room;
import com.hanwha.solbangulrest.user.domain.User;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.dto.PasswordResetDto;
import com.hanwha.solbangulrest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class JoinService {

	private final UserRepository userRepository;
	private final HanwhaUserRepository hanwhaUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final FileStore fileStore;

	@Transactional
	public Long join(JoinUserDto joinUserDto) {
		validateJoinUser(joinUserDto);

		HanwhaUser hanwhaUser = getHanwhaUser(joinUserDto.getGitEmail());
		setEncodePassword(joinUserDto);
		setProfileImage(joinUserDto);
		User user = joinUserDto.toEntity(hanwhaUser);

		// 회원가입 시, room 자동으로 생성
		Room room = createRoom(user);
		user.addRoom(room);

		hanwhaUser.createAccount();
		userRepository.save(user);

		return user.getId();
	}

	private HanwhaUser getHanwhaUser(String gitEmail) {
		HanwhaUser hanwhaUser = hanwhaUserRepository.findHanwhaUserByGitEmail(gitEmail)
			.orElseThrow(() -> new IllegalArgumentException("한화 SW교육 5기생만 가입 가능합니다."));

		if (hanwhaUser.getIsMember() == null || hanwhaUser.getIsMember()) {
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");
		}

		return hanwhaUser;
	}

	private void validateJoinUser(JoinUserDto joinUserDto) {
		if (!joinUserDto.getPassword().equals(joinUserDto.getPasswordConfirm())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		if (userRepository.existsByLoginId(joinUserDto.getLoginId())) {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
		}

		if (userRepository.existsByNickname(joinUserDto.getNickname())) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}
	}

	private void setEncodePassword(JoinUserDto joinUserDto) {
		String encodePassword = passwordEncoder.encode(joinUserDto.getPassword());
		joinUserDto.setEncodedPassword(encodePassword);
	}

	private void setProfileImage(JoinUserDto joinUserDto) {
		UploadFile uploadFile = fileStore.storeFile(joinUserDto.getMultipartFile());
		if (uploadFile != null) {
			joinUserDto.setProfileImage(uploadFile.getStoreFilename());
		} else {
			joinUserDto.setProfileImage("default.png");
		}
	}

	private static Room createRoom(User user) {
		return Room.builder()
			.roomName(user.getNickname() + "의 방")
			.introduction("안녕하세요! " + user.getNickname() + "의 방입니다.")
			.build();
	}
	
	public void passwordReset(String gitEmail, PasswordResetDto passwordResetDto) {
		User user = userRepository.findByGitEmail(gitEmail)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		String encodePassword = passwordEncoder.encode(passwordResetDto.getResetPassword());
		user.updatePassword(encodePassword);
	}
}

