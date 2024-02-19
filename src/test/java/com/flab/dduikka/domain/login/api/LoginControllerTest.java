package com.flab.dduikka.domain.login.api;

import static org.assertj.core.api.BDDAssertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.flab.dduikka.domain.login.application.LoginService;
import com.flab.dduikka.domain.login.dto.LoginRequestDto;
import com.flab.dduikka.domain.login.dto.SessionMember;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	@InjectMocks
	private LoginController loginController;

	@Mock
	private LoginService loginService;

	@Test
	@DisplayName("로그인 요청을 하면 세션이 발급된다")
	void whenLogInThenPublishSession() {
		//given
		LoginRequestDto request = new LoginRequestDto("test@dduikka.com", "1234");
		SessionMember sessionMember = new SessionMember(1L, "test@dduikka.com");
		BDDMockito.given(loginService.login(request))
			.willReturn(sessionMember);
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

		//when
		loginController.login(request, httpServletRequest);

		//then
		HttpSession session = httpServletRequest.getSession();
		assert session != null;
		Object attribute = session.getAttribute(SessionKey.LOGIN_USER.name());
		then(attribute).isEqualTo(sessionMember);
	}

	@Test
	@DisplayName("잘못된 로그인 요청을 하면 세션을 발급하지 않는다")
	void whenLoginThenDontPublishSession() {
		//given
		LoginRequestDto request = new LoginRequestDto("test@dduikka.com", "1234");
		BDDMockito.given(loginService.login(request))
			.willReturn(null);
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

		//when
		loginController.login(request, httpServletRequest);

		//then
		HttpSession session = httpServletRequest.getSession();
		assert session != null;
		Object attribute = session.getAttribute(SessionKey.LOGIN_USER.name());
		then(attribute).isNull();
	}

	@Test
	@DisplayName("잘못된 로그인 요청을 하면 UNAUTHORIZED로 응답한다")
	void whenLoginThenResponseUnauthorizedStatus() {
		//given
		LoginRequestDto request = new LoginRequestDto("test@dduikka.com", "1234");
		BDDMockito.given(loginService.login(request))
			.willReturn(null);
		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

		//when
		ResponseEntity<Void> response = loginController.login(request, httpServletRequest);

		//then
		then(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}

	@Test
	@DisplayName("로그아웃을 하면 세션이 만료된다")
	void whenLogOutThenSessionIsInvalid() {
		//given
		MockHttpServletRequest servletRequest = new MockHttpServletRequest();

		//when
		loginController.logout(servletRequest);

		//then
		HttpSession session = servletRequest.getSession();
		assert session != null;
		Object attribute = session.getAttribute(SessionKey.LOGIN_USER.name());
		then(attribute).isNull();
	}

	@Test
	@DisplayName("세션이 만료된 상태에서 로그아웃을 하면 BADREQUEST로 응답한다")
	void whenLogOutThenResponseBADREQUESTStatus() throws ServletException {
		//given
		MockHttpServletRequest servletRequest = new MockHttpServletRequest();
		HttpSession session = servletRequest.getSession();
		assert session != null;
		session.invalidate();

		//when
		ResponseEntity<Void> response = loginController.logout(servletRequest);

		//then
		then(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

	}

}
