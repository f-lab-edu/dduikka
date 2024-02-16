package com.flab.dduikka.domain.helper;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.dduikka.domain.member.api.MemberController;
import com.flab.dduikka.domain.member.application.MemberService;
import com.flab.dduikka.domain.vote.api.VoteController;
import com.flab.dduikka.domain.vote.application.VoteRecordService;

@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs
@WebMvcTest(controllers =
	{
		VoteController.class
		, MemberController.class
	})
public abstract class ApiDocumentationHelper {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected VoteRecordService voteRecordService;

	@MockBean
	protected MemberService memberService;

	@Autowired
	protected ObjectMapper objectMapper;

}
