package com.flab.dduikka.domain.member.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;

import com.flab.dduikka.domain.helper.ApiDocumentationHelper;
import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.dto.MemberRegisterRequestDTO;
import com.flab.dduikka.domain.member.dto.MemberResponseDTO;

class MemberDocumentationTest extends ApiDocumentationHelper {

	@Test
	@DisplayName("회원을 조회한다")
	void findMember() throws Exception {
		//given
		long memberId = 1L;
		Member mockMember = Member.builder()
			.memberId(memberId)
			.email("dduikka@dduikka.com")
			.password("1234")
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		MemberResponseDTO response = MemberResponseDTO.from(mockMember);
		given(memberService.findMember(anyLong()))
			.willReturn(response);

		MockHttpSession mockSession = new MockHttpSession();
		mockSession.setAttribute(
			SessionKey.LOGIN_USER.name(), new SessionMember(mockMember.getMemberId(), mockMember.getEmail())
		);

		//when,then
		mockMvc.perform(get("/members/me").session(mockSession))
			.andDo(print())
			.andDo(document("members/findMember",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				responseFields(
					fieldWithPath("headers").description("헤더")
					, fieldWithPath("body.memberId").description("회원아이디")
					, fieldWithPath("body.email").description("이메일")
					, fieldWithPath("body.joinDate").description("가입일자")
					, fieldWithPath("statusCodeValue").description("결과코드값")
					, fieldWithPath("statusCode").description("결과코드")
				)))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("이메일 중복여부를 체크한다")
	void isDuplicatedEmail() throws Exception {
		String email = "test@test.com";
		given(memberService.isEmailDuplicated(email))
			.willReturn(false);

		mockMvc.perform(get("/members/{email}/duplicated", email))
			.andDo(print())
			.andDo(document("members/isDuplicatedEmail",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(parameterWithName("email").description("중복여부 조회할 이메일")),
				responseBody()
				// responseFields(
				// 	fieldWithPath("duplicated").description("중복 여부")
				// )
			))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입을 한다")
	void registerMember() throws Exception {
		MemberRegisterRequestDTO request = new MemberRegisterRequestDTO(
			"test@dduikka.net",
			"12345678Qw!@"
		);

		mockMvc.perform(post("/members")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andDo(document("members/create",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("email").description("이메일"),
					fieldWithPath("password").description("비밀번호")
				)))
			.andExpect(status().isCreated());
	}
}
