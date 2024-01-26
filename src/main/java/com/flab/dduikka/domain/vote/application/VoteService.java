package com.flab.dduikka.domain.vote.application;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;

	// TODO : 테스트 진행 필요
	@Scheduled(cron = "0 0 23 * * *")
	public void runSchedule() {
		LocalDate nextDate = LocalDate
			.now()
			.plusDays(1);
		createVote(nextDate);
	}

	public void createVote(LocalDate voteDate) {
		Vote newVote = new Vote(voteDate);
		voteRepository.createVote(newVote);
	}
}
