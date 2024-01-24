package com.flab.dduikka.domain.vote.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.flab.dduikka.domain.vote.domain.Vote;

public interface VoteRepository {

	Vote createVote(Vote vote);

	Optional<Vote> findByDate(LocalDate date);
}
