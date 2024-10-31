package com.flab.dduikka.domain.vote.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.flab.dduikka.domain.helper.IntegrationTestHelper;
import com.flab.dduikka.domain.vote.domain.Vote;

class JdbcVoteRepositoryTest extends IntegrationTestHelper {

	@Autowired
	private VoteRepository voteRepository;

	@Test
	void 투표가_생성된다() {
		//given
		Vote aVote = Vote.builder().
			voteId(1L).
			voteDate(LocalDate.of(2023, 12, 28)).
			createdAt(LocalDateTime.now()).
			build();
		Vote anotherVote = new Vote(LocalDate.of(2023, 12, 29));

		//when
		Vote createdVote = voteRepository.createVote(aVote);
		Vote createdVote2 = voteRepository.createVote(anotherVote);

		//then
		assertThat(createdVote.getVoteId()).isEqualTo(aVote.getVoteId());
		assertThat(createdVote2.getVoteId()).isNotNull();
	}

	@Test
	void 같은_날짜로_투표를_생성하면_중복키예외가_발생한다() {
		//given
		Vote aVote = new Vote(LocalDate.of(2023, 12, 29));
		Vote anotherVote = new Vote(LocalDate.of(2023, 12, 29));

		//when
		voteRepository.createVote(aVote);

		//then
		assertThatThrownBy(() -> voteRepository.createVote(anotherVote))
			.isInstanceOf(DuplicateKeyException.class);
	}

	@Test
	void 날짜로_투표를_조회한다() {
		//given
		Vote aVote = new Vote(LocalDate.of(2023, 12, 29));

		//when
		Vote createdVote = voteRepository.createVote(aVote);
		Optional<Vote> foundedVote = voteRepository.findByDate(createdVote.getVoteDate());

		//then
		assertThat(foundedVote.get().getVoteDate()).isEqualTo(createdVote.getVoteDate());
	}

	@Test
	void 등록되지_않은_날짜로_조회하면_empty가_반환된다() {
		//given
		Vote aVote = new Vote(LocalDate.of(2023, 12, 29));
		Vote anotherVote = new Vote(LocalDate.of(2023, 12, 28));
		voteRepository.createVote(anotherVote);

		//when
		Optional<Vote> foundedVote = voteRepository.findByDate(aVote.getVoteDate());

		//then
		assertThat(foundedVote).isEmpty();
	}

}
