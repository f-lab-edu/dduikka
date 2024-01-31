package com.flab.dduikka.domain.vote.application;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteRecords;
import com.flab.dduikka.domain.vote.dto.VoteRecordAddRequestDto;
import com.flab.dduikka.domain.vote.dto.VoteRecordResponseDto;
import com.flab.dduikka.domain.vote.dto.VoteResponseDto;
import com.flab.dduikka.domain.vote.repository.VoteRecordRepository;
import com.flab.dduikka.domain.vote.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteRecordService {

	private final VoteRepository voteRepository;
	private final VoteRecordRepository voteRecordRepository;

	public void addVoteRecord(VoteRecordAddRequestDto request) {
		Optional<VoteRecord> foundVote = voteRecordRepository
			.findByUserAndVoteAndIsCanceled(request.getUserId(), request.getUserId());
		if (foundVote.isPresent()) {
			throw new IllegalStateException("이미 투표한 유저입니다.");
		}
		VoteRecord entity = VoteRecordAddRequestDto.toEntity(request);
		voteRecordRepository.createVoteRecord(entity);
	}

	// TODO : voteId로 투표 존재 여부 확인 필요
	public VoteRecordResponseDto findUserVoteRecord(final long userId, final long voteId) {
		Optional<VoteRecord> voteRecord = voteRecordRepository
			.findByUserAndVoteAndIsCanceled(userId, voteId);
		return voteRecord.map(VoteRecordResponseDto::toDto).orElse(null);
	}

	public VoteResponseDto findVoteTypeCount(LocalDate voteDate) {
		Vote foundVote = voteRepository.findByDate(voteDate)
			.orElseThrow(() -> new NoSuchElementException("해당 날짜에 투표가 존재하지 않습니다. date" + voteDate));
		VoteRecords voteRecords = new VoteRecords(voteRecordRepository.findAllByVoteId(foundVote.getVoteId()));
		return VoteResponseDto.toDto(foundVote, voteRecords.countsVoteRecords());
	}

	public void cancelVoteRecord(long voteRecordId) {
		VoteRecord foundVoteRecord = voteRecordRepository.findById(voteRecordId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 투표 기록입니다. voteRecordId" + voteRecordId));
		foundVoteRecord.cancel();
		voteRecordRepository.cancelVoteRecord(foundVoteRecord);
	}
}
