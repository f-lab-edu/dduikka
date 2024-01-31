package com.flab.dduikka.domain.vote.application;

import java.time.LocalDate;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoteScheduler {

	private final VoteService voteService;

	@Scheduled(cron = "${schedules.cron-expression.publisher}")
	public void createVoteBySchedule() {
		LocalDate localDate = LocalDate.now();
		try {
			log.info("scheduler 수행 날짜 - {}", localDate);
			LocalDate nextDate = localDate
				.plusDays(1);
			voteService.createVote(nextDate);
		} catch (DuplicateKeyException e) {
			log.error("scheduler 예외 발생 - {}", e.toString());
		}
	}
}
