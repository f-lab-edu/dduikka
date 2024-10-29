package com.flab.dduikka.domain.vote.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class VoteRecordsTest {

	private VoteRecords voteRecords;

	@Test
	void 투표기록이_존재하면_투표_타입별로_투표수를_반환한다() {
		//given
		voteRecords = new VoteRecords(createVoteRecordList());

		//when
		Map<VoteType, Integer> voteTypeCounts = voteRecords.countsVoteRecords();

		//then
		assertThat(voteTypeCounts)
			.containsEntry(VoteType.RUN, 2)
			.containsEntry(VoteType.NORUN, 1);
	}

	@Test
	void 투표기록이_존재하지_않아도_투표_타입별로_0표를_반환한다() {
		//given
		List<VoteRecord> emptyList = new ArrayList<>();
		voteRecords = new VoteRecords(emptyList);

		//when
		Map<VoteType, Integer> voteTypeCounts = voteRecords.countsVoteRecords();

		//then
		assertThat(voteTypeCounts)
			.containsEntry(VoteType.RUN, 0)
			.containsEntry(VoteType.NORUN, 0);
	}

	@Test
	void 투표기록이_하나만_존재해도_투표_타입별로_투표수를_반환한다() {
		//given
		List<VoteRecord> mockVoteRecords = new ArrayList<>();
		VoteRecord voteRecord1 = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.build();
		mockVoteRecords.add(voteRecord1);
		voteRecords = new VoteRecords(mockVoteRecords);

		//when
		Map<VoteType, Integer> voteTypeCounts = voteRecords.countsVoteRecords();

		//then
		assertThat(voteTypeCounts)
			.containsEntry(VoteType.RUN, 1)
			.containsEntry(VoteType.NORUN, 0);
	}

	private List<VoteRecord> createVoteRecordList() {
		List<VoteRecord> mockVoteRecordList = new ArrayList<>();
		VoteRecord voteRecord1 = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.memberId(1L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.build();
		VoteRecord voteRecord2 = VoteRecord.builder()
			.voteRecordId(2L)
			.voteId(1L)
			.memberId(2L)
			.voteType(VoteType.RUN)
			.canceledFlag(false)
			.build();
		VoteRecord voteRecord3 = VoteRecord.builder()
			.voteRecordId(3L)
			.voteId(1L)
			.memberId(3L)
			.voteType(VoteType.NORUN)
			.canceledFlag(false)
			.build();
		mockVoteRecordList.add(voteRecord1);
		mockVoteRecordList.add(voteRecord2);
		mockVoteRecordList.add(voteRecord3);
		return mockVoteRecordList;
	}

}
