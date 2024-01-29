package com.flab.dduikka.domain.vote.domain;

import java.time.LocalDateTime;

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

	public void cancel() {
		this.isCanceled = true;
	}

}
