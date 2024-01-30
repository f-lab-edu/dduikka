package com.flab.dduikka.domain.vote.application;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;

	public void createVote(LocalDate voteDate) {
		Vote newVote = new Vote(voteDate);
		voteRepository.createVote(newVote);
	}
}
