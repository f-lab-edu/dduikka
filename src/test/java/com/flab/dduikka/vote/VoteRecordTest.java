package com.flab.dduikka.vote;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.flab.dduikka.User;
import com.flab.dduikka.user.StubUser;

public class VoteRecordTest {

	VoteRecord voteRecord;

	@Test
	void 투표_기록을_정상적으로_추가한다() {
		// case
		Vote vote = new StubVote(LocalDate.of(2023, 12, 28));
		User user = new StubUser("testUser");
		voteRecord = new VoteRecord(user, vote, VoteType.RUN);

		// when
		voteRecord.addVoteRecord(user, VoteType.RUN);

		// then
		VoteRecord foundVoteRecord = voteRecord.findVoteRecordByUserAndVote(user, VoteType.RUN);
		assertThat(foundVoteRecord).isEqualTo(voteRecord);
	}

	@Test
	void 투표_기록을_정상적으로_취소한다() {
		//case
		Vote vote = new StubVote(LocalDate.of(2023, 12, 28));
		User user = new StubUser("testUser");
		voteRecord = new VoteRecord(user, vote, VoteType.RUN);
		voteRecord.addVoteRecord(user, VoteType.RUN);

		//when
		voteRecord.deleteVoteRecord();

		// then
		VoteRecord foundVoteRecord = voteRecord.findVoteRecordByUserAndVote(user, VoteType.RUN);
		assertThat(foundVoteRecord).isNull();
	}

	static class StubVote extends Vote {

		public StubVote(LocalDate date) {
			super(date);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}
	}
}
