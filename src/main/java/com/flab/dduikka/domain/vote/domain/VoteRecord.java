package com.flab.dduikka.domain.vote.domain;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.vote.dto.VoteRecordAddRequestDto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteRecord {

	private Long voteRecordId;

	private Long voteId;

	private Long userId;

	@Enumerated(EnumType.STRING)
	private VoteType voteType;

	private Boolean isCanceled;

	private LocalDateTime createdAt;

	@Builder
	public VoteRecord(Long voteRecordId, Long voteId, Long userId, VoteType voteType, boolean isCanceled,
		LocalDateTime createdAt) {
		this.voteRecordId = voteRecordId;
		this.voteId = voteId;
		this.userId = userId;
		this.voteType = voteType;
		this.isCanceled = isCanceled;
		this.createdAt = createdAt;
	}

	public static VoteRecord toEntity(VoteRecordAddRequestDto request) {
		return VoteRecord.builder()
			.voteId(request.getVoteId())
			.userId(request.getUserId())
			.voteType(request.getVoteType())
			.isCanceled(request.isCanceled())
			.createdAt(LocalDateTime.now())
			.build();
	}

	public void cancel() {
		this.isCanceled = true;
	}

}
