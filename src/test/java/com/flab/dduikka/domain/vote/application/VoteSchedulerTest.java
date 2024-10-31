package com.flab.dduikka.domain.vote.application;

import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoteSchedulerTest {

	@InjectMocks
	private VoteScheduler scheduler;

	@Mock
	private VoteService voteService;

	@Test
	@DisplayName("스케줄러가 호출되면 createVote가 호출된다_성공")
	void createVoteBySchedule_sucees() {
		// given
		doNothing().when(voteService).createVote(any());

		// when
		scheduler.createVoteBySchedule();

		// then
		then(voteService).should(times(1)).createVote(any());
	}

}
