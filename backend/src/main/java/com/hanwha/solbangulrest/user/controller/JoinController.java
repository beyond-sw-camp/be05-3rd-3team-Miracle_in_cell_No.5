package com.hanwha.solbangulrest.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hanwha.solbangulrest.global.common.Result;
import com.hanwha.solbangulrest.mail.dto.EmailRequestDto;
import com.hanwha.solbangulrest.mail.service.MailSendService;
import com.hanwha.solbangulrest.user.dto.JoinUserDto;
import com.hanwha.solbangulrest.user.dto.PasswordResetDto;
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

	@ExceptionHandler
	public Result<Void> mailCheckExceptionHandler(MailCheckException e) {
		return new Result<>(false, e.getMessage(), null);
	}

	@PostMapping
	public Result<Void> join(@RequestBody @Valid JoinUserDto joinUserDto,
		@SessionAttribute(name = "email", required = false) String email, HttpSession session) {
		if (email == null) {
			throw new MailCheckException("먼저 메일 인증을 해주세요.");
		}

		if (!email.equals(joinUserDto.getGitEmail())) {
			throw new MailCheckException("잘못된 이메일입니다.");
		}

		log.info("회원가입={}", joinUserDto);
		joinService.join(joinUserDto);

		session.removeAttribute("email");
		return new Result<>(true, "회원가입이 완료되었습니다.", null);
	}

	@PostMapping("/mail/send")
	public Result<Void> sendMail(@RequestBody @Valid EmailRequestDto emailRequestDto) {
		if (joinService.isMember(emailRequestDto)) {
			throw new MailCheckException("이미 가입된 이메일입니다.");
		}
		log.info("메일 전송={}", emailRequestDto.getEmail());
		mailService.sendEmail(emailRequestDto.getEmail());

		return new Result<>(true, "메일 전송에 성공했습니다.", null);
	}

	@PostMapping("/mail/send/password-reset")
	public Result<Void> sendMailForPasswordReset(@RequestBody @Valid EmailRequestDto emailRequestDto) {
		log.info("비밀번호 변경을 위한 메일 전송={}", emailRequestDto.getEmail());
		mailService.sendEmail(emailRequestDto.getEmail());

		return new Result<>(true, "메일 전송에 성공했습니다.", null);
	}

	@PostMapping("/mail/check")
	public Result<Void> mailCheck(@RequestBody @Valid EmailRequestDto emailRequestDto, HttpSession session) {
		boolean Checked = mailService.CheckAuthNum(emailRequestDto.getEmail(), emailRequestDto.getAuthNum());
		if (!Checked) {
			throw new MailCheckException("메일 인증에 실패했습니다.");
		}

		// 이메일 인증이 성공하면 세션 생성
		session.setAttribute("email", emailRequestDto.getEmail());
		session.setMaxInactiveInterval(60 * 10);

		return new Result<>(true, "메일 인증에 성공했습니다.", null);
	}

	@GetMapping("/loginId/check")
	public Result<Void> isExistsByLoginId(@RequestBody JoinUserDto joinUserDto) {
		Boolean existsByLoginId = joinService.isExistsByLoginId(joinUserDto);
		if (existsByLoginId) {
			return new Result<>(false, "이미 존재하는 아이디입니다.", null);
		}
		return new Result<>(true, "사용 가능한 아이디입니다.", null);
	}

	@PatchMapping("/password-reset")
	public Result<Void> passwordReset(@RequestBody @Valid PasswordResetDto passwordResetDto,
		@SessionAttribute(name = "email", required = false) String email, HttpSession session) {
		if (email == null) {
			throw new MailCheckException("메일 인증을 해주세요.");
		}

		log.info("비밀번호 재설정={}", passwordResetDto);
		joinService.passwordReset(email, passwordResetDto);

		session.removeAttribute("email");
		return new Result<>(true, "비밀번호가 변경되었습니다.", null);
	}
}
