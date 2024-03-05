package com.flab.dduikka.domain.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.flab.dduikka.domain.livechat.repository.LiveChatRepository;
import com.flab.dduikka.domain.member.repository.MemberRepository;
import com.flab.dduikka.domain.vote.repository.VoteRecordRepository;
import com.flab.dduikka.domain.vote.repository.VoteRepository;

@SpringBootTest
@ActiveProfiles("local")
@Sql(scripts = "classpath:h2/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class SpringBootRepositoryTestHelper {

	@Autowired
	protected VoteRepository voteRepository;

	@Autowired
	protected VoteRecordRepository voteRecordRepository;

	@Autowired
	protected MemberRepository memberRepository;

	@Autowired
	protected LiveChatRepository liveChatRepository;

}
