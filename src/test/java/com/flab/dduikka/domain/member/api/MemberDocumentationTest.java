package com.flab.dduikka.domain.member.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import com.flab.dduikka.domain.helper.ApiDocumentationHelper;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;

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
		given(memberService.findMember(anyLong()))
			.willReturn(MemberResponseDto.from(mockMember));

		//when,then
		mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{memberId}", memberId))
			.andDo(print())
			.andDo(document("members/findMember",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(parameterWithName("memberId").description("회원아이디")),
				responseFields(
					fieldWithPath("memberId").description("회원아이디")
					, fieldWithPath("email").description("이메일")
					, fieldWithPath("joinDate").description("가입일자")
				)))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("이메일 중복여부를 체크한다")
	void isDuplicatedEmail() throws Exception {
		String email = "test@test.com";
		given(memberService.isEmailDuplicated(email))
			.willReturn(false);

		mockMvc.perform(RestDocumentationRequestBuilders.get("/members/{email}/duplicated", email))
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
}
