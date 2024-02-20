package com.flab.dduikka.domain.login.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.login.application.LoginService;
import com.flab.dduikka.domain.login.dto.LoginRequestDto;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.login.exception.LoginException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
		SessionMember sessionMember = loginService.login(loginRequestDto);
		if (sessionMember == null) {
			throw new LoginException.FailLoginException("찾을 수 없는 회원입니다.");
		}
		HttpSession session = request.getSession(true);
		session.setAttribute(SessionKey.LOGIN_USER.name(), sessionMember);
		return ResponseEntity.ok()
			.build();
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new IllegalStateException("유효하지 않은 요청입니다.");
		}
		session.invalidate();
		return ResponseEntity.ok()
			.build();
	}
}
