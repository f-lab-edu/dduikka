package com.flab.dduikka.domain.login.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.flab.dduikka.domain.helper.ApiDocumentationHelper;
import com.flab.dduikka.domain.login.dto.LoginRequestDto;
import com.flab.dduikka.domain.login.dto.SessionMember;

import jakarta.servlet.http.HttpSession;

class LoginDocumentationTest extends ApiDocumentationHelper {

	@Test
	@DisplayName("로그인한다")
	void login() throws Exception {
		//given
		LoginRequestDto request =
			new LoginRequestDto("test@dduikka.net", "1234");
		SessionMember sessionMember =
			new SessionMember(1L, request.getEmail());
		given(loginService.login(any()))
			.willReturn(sessionMember);

		//when,then
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andDo(document("login",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("email").description("이메일")
					, fieldWithPath("password").description("패스워드")
				)))
			.andExpect(status().isOk())
			.andReturn();

		HttpSession session = result.getRequest().getSession();
		assert session != null;
		BDDAssertions.then(sessionMember).isEqualTo(session.getAttribute(SessionKey.LOGIN_USER.name()));
	}

	@Test
	@DisplayName("로그아웃한다")
	void logout() throws Exception {
		//given
		SessionMember sessionMember =
			new SessionMember(1L, "test@dduikka.net");

		//when, then
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/logout")
				.sessionAttr(SessionKey.LOGIN_USER.name(), sessionMember))
			.andDo(print())
			.andDo(document("login",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())
			))
			.andExpect(status().isOk())
			.andReturn();
		HttpSession session = result.getRequest().getSession();
		assert session != null;
		Object attribute = session.getAttribute(SessionKey.LOGIN_USER.name());
		BDDAssertions.then(attribute).isNull();
	}

}
