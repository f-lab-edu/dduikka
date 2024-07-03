package com.flab.dduikka.domain.login.api;

import static org.assertj.core.api.BDDAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.flab.dduikka.domain.helper.IntegrationTestHelper;
import com.flab.dduikka.domain.login.dto.LoginRequestDTO;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.login.exception.LoginException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

class LoginControllerTest extends IntegrationTestHelper {

	@Test
	@DisplayName("로그인 요청을 하면 세션이 발급된다")
	void whenLogInThenPublishSession() throws Exception {
		//given
		LoginRequestDTO request = new LoginRequestDTO("test@dduikka.com", "1234");
		SessionMember sessionMember = new SessionMember(1L, "test@dduikka.com");
		BDDMockito.given(loginService.login(any())).willReturn(sessionMember);

		//when
		MvcResult result = mockMvc.perform(
				post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();

		//then
		HttpSession session = result.getRequest().getSession();
		assert session != null;
		Object attribute = session.getAttribute(SessionKey.LOGIN_USER.name());
		then(attribute).isEqualTo(sessionMember);
	}

	@Test
	@DisplayName("잘못된 로그인 요청을 하면 UNAUTHORIZED로 응답한다")
	void whenLoginThenResponseUnauthorizedStatus() throws Exception {
		//given
		LoginRequestDTO request = new LoginRequestDTO("test@dduikka.com", "1234");
		BDDMockito.given(loginService.login(any())).willReturn(null);

		//when
		mockMvc.perform(post("/login")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
			.andExpect(
				result -> assertInstanceOf(LoginException.FailLoginException.class, result.getResolvedException()))
			.andDo(print())
			.andReturn();
	}

	@Test
	@DisplayName("로그아웃을 하면 세션이 만료된다")
	void whenLogOutThenSessionIsInvalid() throws Exception {
		//given
		LoginRequestDTO request = new LoginRequestDTO("test@dduikka.com", "1234");
		SessionMember sessionMember = new SessionMember(1L, "test@dduikka.com");
		BDDMockito.given(loginService.login(any())).willReturn(sessionMember);

		//when
		MvcResult result = mockMvc.perform(post("/logout").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.sessionAttr(SessionKey.LOGIN_USER.name(), sessionMember))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();

		//then
		HttpSession session = result.getRequest().getSession();
		assert session != null;
		Object attribute = session.getAttribute(SessionKey.LOGIN_USER.name());
		then(attribute).isNull();
	}

	@Test
	@DisplayName("세션이 만료된 상태에서 로그아웃을 하면 IllegalStateException 예외가 발생한다")
	void whenLogOutThenThrowsIllegalStateExceptionException() throws Exception {
		mockMvc.perform(post("/logout")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(result -> assertInstanceOf(IllegalStateException.class, result.getResolvedException()))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("로그인을 하면 쿠키가 생성된다")
	void whenLogInThenAddCookies() throws Exception {
		//given
		LoginRequestDTO request = new LoginRequestDTO("test@dduikka.com", "1234");
		SessionMember sessionMember = new SessionMember(1L, "test@dduikka.com");
		String eid = "1234";
		BDDMockito.given(loginService.login(any())).willReturn(sessionMember);
		BDDMockito.given(cachedEncryptor.cacheEncryptedMemberIdentifier(any())).willReturn(eid);
		Cookie createdCookie =
			new Cookie("EID",
				cachedEncryptor.cacheEncryptedMemberIdentifier(String.valueOf(sessionMember.getMemberId())));
		createdCookie.setSecure(false);
		createdCookie.setHttpOnly(false);

		//when
		MvcResult result = mockMvc.perform(
				post("/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn();
		boolean match =
			Arrays.asList(result.getResponse().getCookies())
				.contains(createdCookie);

		//then
		assertThat(match).isTrue();
	}

}
