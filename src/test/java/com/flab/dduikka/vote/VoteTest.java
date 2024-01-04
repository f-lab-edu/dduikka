package com.flab.dduikka.vote;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.flab.dduikka.User;
import com.flab.dduikka.user.StubUser;

// TODO 테스트 코드로 변경하기
public class VoteTest {

	private Vote vote;

	@Test
	void 투표_추가시_투표기록과_투표내용을_추가해야_한다() {
		//given
		vote = new Vote(LocalDate.of(2023, 12, 28));
		User user = new StubUser("testId");
		User user2 = new StubUser("testId2");
		VoteRecord mockVoteRecord = mock(VoteRecord.class);
		VoteRecord mockVoteRecord2 = mock(VoteRecord.class);

		//when
		vote.addVote(user, VoteType.RUN, mockVoteRecord);
		vote.addVote(user2, VoteType.RUN, mockVoteRecord2);

		//then
		assertThat(vote.getVoteContentCount(VoteType.RUN)).isEqualTo(2);
		List<VoteRecord> foundVoteRecords = vote.findVoteRecords();
		assertThat(foundVoteRecords).contains(mockVoteRecord);
	}

	@Test
	void 투표_추가시_투표기록과_투표내용을_호출해야_한다() {
		// given
		vote = new Vote(LocalDate.of(2023, 12, 28));
		User user = new StubUser("testId");
		VoteRecord mockVoteRecord = mock(VoteRecord.class);

		// when
		vote.addVote(user, VoteType.RUN, mockVoteRecord);

		// then
		verify(mockVoteRecord, times(1)).addVoteRecord(user, VoteType.RUN);
	}

	@Test
	void 존재하지_않는_날짜로_투표를_조회하면_null이_반환되어야_한다() {
		//given
		vote = new Vote(LocalDate.of(2023, 12, 28));

		//when
		Vote anotherVote = vote.findVoteByDate(LocalDate.of(2024, 1, 1));

		//then
		assertThat(anotherVote).isNull();
	}

	@Test
	void 투표_취소시_투표기록과_투표내용이_삭제되어야_한다() {
		// given
		vote = new Vote(LocalDate.of(2023, 12, 28));
		User user = new StubUser("testId");
		VoteRecord mockVoteRecord = mock(VoteRecord.class);
		vote.addVote(user, VoteType.RUN, mockVoteRecord);

		// when
		vote.cancelVote(VoteType.RUN, mockVoteRecord);

		// then
		int voteContentCount = vote.getVoteContentCount(VoteType.RUN);
		assertThat(voteContentCount).isEqualTo(0);
		List<VoteRecord> voteRecords = vote.findVoteRecords();
		assertThat(voteRecords).doesNotContain(mockVoteRecord);
	}

	@Test
	void 투표_취소시_투표기록과_투표내용이_호출되어야_한다() {
		// given
		vote = new Vote(LocalDate.of(2023, 12, 28));
		User user = new StubUser("testId");
		VoteContent mockVoteContent = mock(VoteContent.class);
		VoteRecord mockVoteRecord = mock(VoteRecord.class);
		vote.addVote(user, VoteType.RUN, mockVoteRecord);

		// when
		vote.cancelVote(VoteType.RUN, mockVoteRecord);

		// then
		verify(mockVoteRecord, times(1)).deleteVoteRecord();
	}

}
