package com.flab.dduikka.domain.vote.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteType;
import com.flab.dduikka.domain.vote.dto.VoteRecordAddRequestDto;
import com.flab.dduikka.domain.vote.dto.VoteRecordResponseDto;
import com.flab.dduikka.domain.vote.dto.VoteResponseDto;
import com.flab.dduikka.domain.vote.repository.VoteRecordRepository;
import com.flab.dduikka.domain.vote.repository.VoteRepository;

@ExtendWith(MockitoExtension.class)
class VoteRecordServiceTest {

	@InjectMocks
	private VoteRecordService voteRecordService;
	@Mock
	private VoteRecordRepository voteRecordRepository;
	@Mock
	private VoteRepository voteRepository;

	@Test
	void 유저가_투표하면_투표기록이_추가된다() {
		//given
		VoteRecordAddRequestDto request =
			new VoteRecordAddRequestDto(
				1L,
				1L,
				VoteType.RUN
			);
		VoteRecord mockVoteRecord = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.voteType(VoteType.RUN)
			.isCanceled(false)
			.build();

		given(voteRecordRepository.addVote(any(VoteRecord.class)))
			.willReturn(mockVoteRecord);
		given(voteRecordRepository.findByUserAndVoteAndIsCanceled(anyLong(), anyLong()))
			.willReturn(Optional.empty())
			.willReturn(Optional.of(mockVoteRecord));
		//when
		voteRecordService.addVoteRecord(request);
		VoteRecordResponseDto response = voteRecordService.findUserVoteRecord(1L, 1L);

		//when
		then(response.getVoteRecordId()).isNotZero();
		then(response.getVoteType()).isEqualTo(request.getVoteType());

	}

	@Test
	void 이미_투표한_유저가_투표하면_예외가_발생한다() {
		//given
		VoteRecordAddRequestDto request =
			new VoteRecordAddRequestDto(
				1L,
				1L,
				VoteType.RUN
			);
		VoteRecord mockVoteRecord = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.voteType(VoteType.RUN)
			.isCanceled(false)
			.build();

		given(voteRecordRepository.addVote(any(VoteRecord.class)))
			.willReturn(mockVoteRecord);
		given(voteRecordRepository.findByUserAndVoteAndIsCanceled(anyLong(), anyLong()))
			.willReturn(Optional.empty())
			.willReturn(Optional.ofNullable(mockVoteRecord));

		//when
		voteRecordService.addVoteRecord(request);
		thenThrownBy(
			() -> voteRecordService.addVoteRecord(request)).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void 유저의_투표를_조회한다() {
		//given
		VoteRecord mockVoteRecord = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.voteType(VoteType.RUN)
			.isCanceled(false)
			.build();
		given(voteRecordRepository.findByUserAndVoteAndIsCanceled(anyLong(), anyLong()))
			.willReturn(Optional.of(mockVoteRecord));

		//when
		VoteRecordResponseDto response = voteRecordService.findUserVoteRecord(1L, 1L);

		//then
		then(response.getVoteRecordId()).isNotZero();
		then(response.getVoteType()).isEqualTo(mockVoteRecord.getVoteType());
	}

	@Test
	void 유저가_투표하지_않으면_투표타입이_null로_조회된다() {
		//given
		given(voteRecordRepository.findByUserAndVoteAndIsCanceled(anyLong(), anyLong()))
			.willReturn(Optional.empty());

		//when
		VoteRecordResponseDto response = voteRecordService.findUserVoteRecord(1L, 1L);

		//then
		then(response).isNull();
	}

	@Test
	void 일자로_타입별_투표수를_조회한다() {
		//given
		LocalDate voteDate = LocalDate.of(2024, 1, 22);
		List<VoteRecord> mockVoteRecordList = createVoteRecordList();
		Vote mockVote = Vote.builder()
			.voteId(1L)
			.voteDate(voteDate)
			.build();

		given(voteRecordRepository.findByVoteAndVoteType(any(LocalDate.class)))
			.willReturn(mockVoteRecordList);
		given(voteRepository.findByDate(any(LocalDate.class)))
			.willReturn(Optional.of(mockVote));

		//when
		VoteResponseDto response = voteRecordService.findVoteTypeCount(voteDate);

		//then
		then(response.getVoteTypeCountMap()).containsEntry(VoteType.RUN, 2);
		then(response.getVoteTypeCountMap()).containsEntry(VoteType.NORUN, 1);
	}

	@Test
	void 유저가_투표하지_않아도_타입별_투표수가_조회된다() {
		//given
		LocalDate voteDate = LocalDate.of(2024, 1, 22);
		Vote mockVote = Vote.builder()
			.voteId(1L)
			.voteDate(voteDate)
			.build();
		given(voteRepository.findByDate(any(LocalDate.class)))
			.willReturn(Optional.of(mockVote));

		//when
		VoteResponseDto response = voteRecordService.findVoteTypeCount(voteDate);

		//then
		then(response.getVoteTypeCountMap()).containsEntry(VoteType.RUN, 0);
		then(response.getVoteTypeCountMap()).containsEntry(VoteType.NORUN, 0);
	}

	@Test
	void 유저가_투표를_취소한다() {
		//given
		VoteRecord mockVoteRecord = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.voteType(VoteType.RUN)
			.isCanceled(false)
			.build();

		given(voteRecordRepository.findById(anyLong()))
			.willReturn(Optional.of(mockVoteRecord));
		given(voteRecordRepository.findByUserAndVoteAndIsCanceled(anyLong(), anyLong()))
			.willReturn(Optional.empty());

		//when
		voteRecordService.cancelVoteRecord(1L);

		//then
		VoteRecordResponseDto voteRecord = voteRecordService.findUserVoteRecord(1L, 1L);
		then(voteRecord).isNull();
	}

	private List<VoteRecord> createVoteRecordList() {
		List<VoteRecord> mockVoteRecordList = new ArrayList<>();
		VoteRecord voteRecord1 = VoteRecord.builder()
			.voteRecordId(1L)
			.voteId(1L)
			.userId(1L)
			.voteType(VoteType.RUN)
			.isCanceled(false)
			.build();
		VoteRecord voteRecord2 = VoteRecord.builder()
			.voteRecordId(2L)
			.voteId(1L)
			.userId(2L)
			.voteType(VoteType.RUN)
			.isCanceled(false)
			.build();
		VoteRecord voteRecord3 = VoteRecord.builder()
			.voteRecordId(3L)
			.voteId(1L)
			.userId(3L)
			.voteType(VoteType.NORUN)
			.isCanceled(false)
			.build();
		mockVoteRecordList.add(voteRecord1);
		mockVoteRecordList.add(voteRecord2);
		mockVoteRecordList.add(voteRecord3);
		return mockVoteRecordList;
	}

}
