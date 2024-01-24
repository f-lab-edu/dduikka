package com.flab.dduikka.domain.vote.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.dto.VoteResponseDto;
import com.flab.dduikka.domain.vote.repository.VoteRepository;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

	@InjectMocks // Mock이 붙은 객체를 주입해주는 annotation
	private VoteService voteService;
	@Mock
	private VoteRepository voteRepository;

	@Test
	void 날짜로_투표를_생성한다() {
		// given
		LocalDate date = LocalDate.of(2023, 12, 28);
		Vote mockVote = Vote.builder()
			.voteId(1L)
			.voteDate(date)
			.build();

		given(voteRepository.createVote(any(Vote.class))).willReturn(mockVote);
		given(voteRepository.findByDate(date)).willReturn(Optional.ofNullable(mockVote));

		// when
		voteService.createVote(date);

		// then
		VoteResponseDto foundVote = voteService.findByDate(date);
		assert mockVote != null;
		assertThat(foundVote.getVoteDate()).isEqualTo(mockVote.getVoteDate());
	}

	@Test
	void 같은_날짜로_투표를_생성하면_예외가_발생한다() {
		// given
		LocalDate aDate = LocalDate.of(2023, 12, 28);
		LocalDate sameDate = LocalDate.of(2023, 12, 28);
		Vote mockVote = Vote.builder()
			.voteId(1L)
			.voteDate(aDate)
			.build();

		given(voteRepository.createVote(any(Vote.class))).willReturn(mockVote)
			.willThrow(IllegalStateException.class);

		voteService.createVote(aDate);
		assertThrows(IllegalStateException.class,
			() -> voteService.createVote(sameDate));
	}

	@Test
	void 다른_날짜로_조회하면_오류가_발생한다() {
		// given
		LocalDate date = LocalDate.of(2023, 12, 28);
		LocalDate anotherDate = LocalDate.of(2023, 12, 31);
		Vote mockVote = Vote.builder()
			.voteId(1L)
			.voteDate(date)
			.build();
		given(voteRepository.createVote(any(Vote.class))).willReturn(mockVote);
		given(voteRepository.findByDate(anotherDate))
			.willThrow(NoSuchElementException.class);

		voteService.createVote(date);

		assertThrows(NoSuchElementException.class,
			() -> voteService.findByDate(anotherDate));
	}

}
