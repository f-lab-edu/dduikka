package com.flab.dduikka.domain.vote.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.IntegrationTestHelper;
import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteType;

class JdbcVoteRecordRepositoryTest extends IntegrationTestHelper {

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private VoteRecordRepository voteRecordRepository;

	@Test
	void 유저가_투표하면_투표기록이_생성된다() {
		//given
		VoteRecord newVote = VoteRecord.builder()
			.voteId(1L)
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.createdAt(LocalDateTime.now())
			.build();

		//when
		VoteRecord createdVote = voteRecordRepository.createVoteRecord(newVote);

		//then
		VoteRecord foundVoteRecord = voteRecordRepository
			.findByUserAndVoteAndIsCanceled(1L, 1L).get();
		assertThat(foundVoteRecord).isEqualTo(createdVote);
	}

	@Test
	void 투표한_유저가_조회하면_투표기록이_조회된다() {
		//given
		VoteRecord newVote = VoteRecord.builder()
			.voteId(1L)
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		VoteRecord createdVote = voteRecordRepository.createVoteRecord(newVote);

		//when
		VoteRecord foundVoteRecord = voteRecordRepository
			.findByUserAndVoteAndIsCanceled(1L, 1L).get();

		//then
		assertThat(foundVoteRecord).isEqualTo(createdVote);
	}

	@Test
	void 투표하지_않은_유저가_조회하면_empty로_조회된다() {
		//when
		Optional<VoteRecord> foundVoteRecord = voteRecordRepository
			.findByUserAndVoteAndIsCanceled(1L, 1L);

		//then
		assertThat(foundVoteRecord).isEmpty();
	}

	@Test
	void 날짜로_조회하면_취소되지_않은_투표_기록을_조회한다() {
		//given
		LocalDate voteDate = LocalDate.of(2023, 12, 31);
		Vote newVote = new Vote(voteDate);
		Vote createdVote = voteRepository.createVote(newVote);
		VoteRecord newVoteRecord = VoteRecord.builder()
			.voteId(createdVote.getVoteId())
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		voteRecordRepository.createVoteRecord(newVoteRecord);

		//when
		List<VoteRecord> voteRecords = voteRecordRepository.findAllByVoteId(createdVote.getVoteId());

		//then
		assertThat(voteRecords).hasSize(1);
	}

	@Test
	void 투표_기록_번호로_투표를_조회한다() {
		//given
		LocalDate voteDate = LocalDate.of(2023, 12, 31);
		Vote newVote = new Vote(voteDate);
		Vote createdVote = voteRepository.createVote(newVote);
		VoteRecord newVoteRecord = VoteRecord.builder()
			.voteId(createdVote.getVoteId())
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		VoteRecord createdVoteRecord = voteRecordRepository.createVoteRecord(newVoteRecord);

		// given
		VoteRecord foundVoteRecord = voteRecordRepository
			.findById(createdVoteRecord.getVoteRecordId())
			.get();

		// then
		assertThat(foundVoteRecord).isEqualTo(createdVoteRecord);
	}

	@Test
	void 유저가_투표를_취소한다() {
		//given
		LocalDate voteDate = LocalDate.of(2023, 12, 31);
		Vote newVote = new Vote(voteDate);
		Vote createdVote = voteRepository.createVote(newVote);
		VoteRecord newVoteRecord = VoteRecord.builder()
			.voteId(createdVote.getVoteId())
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		VoteRecord createdVoteRecord = voteRecordRepository.createVoteRecord(newVoteRecord);
		createdVoteRecord.cancel();

		//when
		voteRecordRepository.cancelVoteRecord(createdVoteRecord);

		//given
		Optional<VoteRecord> foundVoteRecord = voteRecordRepository
			.findById(createdVoteRecord.getVoteRecordId());
		assertThat(foundVoteRecord).isEmpty();
	}
}
