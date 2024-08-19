package com.flab.dduikka.domain.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import com.flab.dduikka.domain.livechat.repository.LiveChatRepository;
import com.flab.dduikka.domain.member.repository.MemberRepository;
import com.flab.dduikka.domain.vote.repository.VoteRecordRepository;
import com.flab.dduikka.domain.vote.repository.VoteRepository;
import com.flab.dduikka.domain.weather.repository.WeatherRepository;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:h2/teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@JdbcTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public abstract class JDBCRepositoryTestHelper {

	@Autowired
	protected VoteRepository voteRepository;

	@Autowired
	protected VoteRecordRepository voteRecordRepository;

	@Autowired
	protected MemberRepository memberRepository;

	@Autowired
	protected LiveChatRepository liveChatRepository;

	@Autowired
	protected WeatherRepository weatherRepository;

}
