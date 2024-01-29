package com.flab.dduikka.domain.vote.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.flab.dduikka.domain.vote.domain.VoteRecord;

public interface VoteRecordRepository {

	VoteRecord createVoteRecord(VoteRecord voteRecord);

	Optional<VoteRecord> findByUserAndVoteAndIsCanceled(long userId, long voteId);

	List<VoteRecord> findAllByVoteDate(LocalDate voteDate);

	Optional<VoteRecord> findById(long voteRecordId);

	void cancelVoteRecord(VoteRecord foundVoteRecord);
}
