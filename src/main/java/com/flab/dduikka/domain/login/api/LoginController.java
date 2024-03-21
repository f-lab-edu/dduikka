package com.flab.dduikka.domain.login.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.common.util.SHA256Encryptor;
import com.flab.dduikka.domain.login.application.LoginService;
import com.flab.dduikka.domain.login.dto.LoginRequestDto;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.login.exception.LoginException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public void login(
		@Valid @RequestBody LoginRequestDto loginRequestDto,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		SessionMember sessionMember = loginService.login(loginRequestDto);
		if (sessionMember == null) {
			throw new LoginException.FailLoginException("찾을 수 없는 회원입니다.");
		}
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionKey.LOGIN_USER.name(), sessionMember);
		Cookie cookie =
			new Cookie("EID", SHA256Encryptor.hashSHA256(String.valueOf(sessionMember.getMemberId())));
		response.addCookie(cookie);

	}

	@PostMapping("/logout")
	@ResponseStatus(HttpStatus.OK)
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new IllegalStateException("유효하지 않은 요청입니다.");
		}
		session.invalidate();
	}
}
