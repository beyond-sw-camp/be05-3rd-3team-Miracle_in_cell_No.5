package com.hanwha.solbangulrest.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hanwha.solbangulrest.mail.dto.EmailRequestDto;
import com.hanwha.solbangulrest.mail.service.MailSendService;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.dto.PasswordResetDto;
import com.hanwha.solbangulrest.user.exception.ErrorResult;
import com.hanwha.solbangulrest.user.exception.MailCheckException;
import com.hanwha.solbangulrest.user.service.JoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/join")
@RestController
public class JoinController {

	private final JoinService joinService;
	private final MailSendService mailService;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler
	public ErrorResult mailCheckExceptionHandler(MailCheckException e) {
		return new ErrorResult("MAIL_CHECK_FAILED", e.getMessage());
	}

	@PostMapping
	public void join(@RequestBody @Valid JoinUserDto joinUserDto,
		@SessionAttribute(name = "email", required = false) String email, HttpSession session) {
		if (email == null) {
			throw new MailCheckException("메일 인증을 해주세요.");
		}

		if (!email.equals(joinUserDto.getGitEmail())) {
			throw new MailCheckException("잘못된 이메일입니다.");
		}

		log.info("회원가입={}", joinUserDto);
		joinService.join(joinUserDto);

		session.removeAttribute("email");
	}

	@PostMapping("/mail/send")
	public void sendMail(@RequestBody @Valid EmailRequestDto emailRequestDto) {
		log.info("메일 전송={}", emailRequestDto.getEmail());
		mailService.sendEmail(emailRequestDto.getEmail());
	}

	@PostMapping("/mail/check")
	public void mailCheck(@RequestBody @Valid EmailRequestDto emailRequestDto, HttpSession session) {
		boolean Checked = mailService.CheckAuthNum(emailRequestDto.getEmail(), emailRequestDto.getAuthNum());
		if (!Checked) {
			throw new MailCheckException("메일 인증에 실패했습니다.");
		}

		// 이메일 인증이 성공하면 세션 생성
		session.setAttribute("email", emailRequestDto.getEmail());
		session.setMaxInactiveInterval(60 * 10);
	}

	@PatchMapping("/password-reset")
	public void passwordReset(@RequestBody @Valid PasswordResetDto passwordResetDto,
		@SessionAttribute(name = "email", required = false) String email, HttpSession session) {
		if (email == null) {
			throw new MailCheckException("메일 인증을 해주세요.");
		}

		log.info("비밀번호 재설정={}", passwordResetDto);
		joinService.passwordReset(email, passwordResetDto);

		session.removeAttribute("email");
	}
}
