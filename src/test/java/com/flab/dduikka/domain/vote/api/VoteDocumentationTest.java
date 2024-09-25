package com.flab.dduikka.domain.vote.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import com.flab.dduikka.domain.helper.ApiDocumentationHelper;
import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.vote.domain.VoteType;
import com.flab.dduikka.domain.vote.dto.VoteRecordResponseDTO;
import com.flab.dduikka.domain.vote.dto.VoteResponseDTO;

class VoteDocumentationTest extends ApiDocumentationHelper {

	@Test
	@DisplayName("투표를 조회한다")
	void findVote() throws Exception {
		//given
		Map<VoteType, Integer> voteTypeCountMap = new EnumMap<>(VoteType.class);
		voteTypeCountMap.put(VoteType.RUN, 1);
		voteTypeCountMap.put(VoteType.NORUN, 3);
		LocalDate voteDate = LocalDate.of(2024, 2, 13);
		VoteResponseDTO mockResponse =
			new VoteResponseDTO(1L, voteDate, voteTypeCountMap);

		given(voteRecordService.findVoteTypeCount(any())).willReturn(mockResponse);

		//when, then
		mockMvc.perform(get("/vote/{voteDate}", voteDate))
			.andDo(print())
			.andDo(document("vote/findVote",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(parameterWithName("voteDate").description("투표일자")),
				responseFields(
					fieldWithPath("headers").description("헤더라인"),
					fieldWithPath("body.voteId").description("투표번호"),   // 필드 설명 snippets 생성
					fieldWithPath("body.voteDate").description("투표일자"),
					fieldWithPath("body.voteTypeCountMap.*").type(JsonFieldType.VARIES)
						.description("투표타입별 투표수 맵 - (VoteType : Integer)"),
					fieldWithPath("statusCode").description("결과코드"),
					fieldWithPath("statusCodeValue").description("결과값")
				)))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("유저의 투표 기록을 가져온다")
	void findRecordVote() throws Exception {
		//given
		VoteRecordResponseDTO mockResponse =
			new VoteRecordResponseDTO(1L, 1L, VoteType.RUN);
		given(voteRecordService.findUserVoteRecord(anyLong(), anyLong()))
			.willReturn(mockResponse);

		MockHttpSession mockSession = new MockHttpSession();
		mockSession.setAttribute(
			SessionKey.LOGIN_USER.name(), new SessionMember(1L, "test@test.com")
		);

		//when,then
		mockMvc.perform(get("/vote/record/{voteId}", 1L).session(mockSession))
			.andDo(print())
			.andDo(document("vote/findVoteRecord",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(parameterWithName("voteId").description("투표번호")),
				responseFields(
					fieldWithPath("headers").description("헤더라인"),
					fieldWithPath("body.voteRecordId").description("투표기록번호"),   // 필드 설명 snippets 생성
					fieldWithPath("body.voteId").description("투표번호"),
					voteTypeFiled("body.voteType"),
					fieldWithPath("statusCode").description("결과코드"),
					fieldWithPath("statusCodeValue").description("결과값")
				)))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("유저의 투표 기록을 등록한다")
	void addVoteRecord() throws Exception {

		Map<String, String> request = new HashMap<>();
		request.put("voteId", "1");
		request.put("userId", "1");
		request.put("voteType", "RUN");

		mockMvc.perform(post("/vote/record")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andDo(document("vote/create",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				requestFields(
					fieldWithPath("voteId").description("투표번호"),
					fieldWithPath("userId").description("유저번호"),
					voteTypeFiled("voteType")
				)))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("유저의 투표를 취소한다")
	void cancelVoteRecord() throws Exception {
		long voteRecordId = 1L;
		mockMvc.perform(patch("/vote/record/{voteRecordId}", voteRecordId))
			.andDo(print())
			.andDo(document("vote/delete",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				pathParameters(parameterWithName("voteRecordId").description("투표기록번호"))))
			.andExpect(status().isOk());
	}

	private FieldDescriptor voteTypeFiled(String fieldPath) {
		String formattedEnumValues = Arrays.stream(VoteType.values())
			.map(type -> String.format("`%s`", type))
			.collect(Collectors.joining(", "));
		return fieldWithPath(fieldPath).description("투표 타입 상세 : " + formattedEnumValues);
	}

}
