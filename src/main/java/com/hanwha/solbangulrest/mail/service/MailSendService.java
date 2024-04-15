package com.hanwha.solbangulrest.mail.service;

import java.util.Random;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hanwha.solbangulrest.hanwhauser.domain.HanwhaUser;
import com.hanwha.solbangulrest.hanwhauser.repository.HanwhaUserRepository;
import com.hanwha.solbangulrest.mail.config.RedisUtil;
import com.hanwha.solbangulrest.user.exception.MailCheckException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailSendService {

	private final JavaMailSender mailSender;
	private int authNumber;

	private final RedisUtil redisUtil;
	private final HanwhaUserRepository hanwhaUserRepository;

	@Value("${spring.mail.username}")
	private String fromMail;

	public String sendEmail(String toMail) {
		validateEmail(toMail);
		makeRandomNumber();
		String title = "솔방울 회원 가입 인증 이메일 입니다.";
		String content =
			"솔방울을 방문해주셔서 감사합니다." +
				"<br>" +
				"인증 번호는 " + authNumber + "입니다." +
				"<br>" +
				"인증번호를 정확히 입력해주세요!";
		sendMail(fromMail, toMail, title, content);
		return Integer.toString(authNumber);
	}

	private void validateEmail(String email) {
		HanwhaUser hanwhaUser = hanwhaUserRepository.findHanwhaUserByGitEmail(email)
			.orElseThrow(() -> new MailCheckException("한화 SW교육 5기생만 가입 가능합니다."));

		if (hanwhaUser.getIsMember() == null || hanwhaUser.getIsMember()) {
			throw new MailCheckException("이미 가입된 이메일입니다.");
		}
	}

	public boolean CheckAuthNum(String email, String authNum) {
		if (redisUtil.getData(authNum) == null) {
			return false;
		} else {
			return redisUtil.getData(authNum).equals(email);
		}
	}

	public void makeRandomNumber() {
		Random r = new Random();
		StringBuilder randomNumber = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			randomNumber.append(r.nextInt(10));
		}

		authNumber = Integer.parseInt(randomNumber.toString());
	}

	public void sendMail(String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			log.error("이메일 전송 실패", e);
		}
		redisUtil.setDataExpire(Integer.toString(authNumber), toMail, 60 * 5L);
	}
}
