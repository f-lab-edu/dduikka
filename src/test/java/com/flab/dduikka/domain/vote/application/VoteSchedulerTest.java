package com.flab.dduikka.domain.vote.application;

import static org.awaitility.Awaitility.*;
import static org.mockito.BDDMockito.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(
	properties = {
		"schedules.cron-expression.publisher= 0/2 * * * * *"
	}
)
@ActiveProfiles(profiles = "local")
@Sql(scripts = "classpath:h2/teardown.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class VoteSchedulerTest {

	@SpyBean
	private VoteScheduler scheduler;

	@Test
	void 스케줄러가_정상_호출된다() {
		await() // 기다린다
			.atMost(Duration.ofSeconds(2)) // 대기 시간 지정
			.untilAsserted( // 실행 통과될 때 까지 대기
				() -> then(scheduler).should(times(1)).createVoteBySchedule()
			);
	}

	@Test
	void 정해진_시간이_아니면_스케줄러가_호출되지_않는다() {
		await()
			.atMost(Duration.ofSeconds(1))
			.untilAsserted(() ->
				then(scheduler).should(never()).createVoteBySchedule()
			);
	}
}
