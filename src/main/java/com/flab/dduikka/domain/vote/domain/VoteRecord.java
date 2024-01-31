package com.flab.dduikka.domain.vote.domain;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.BaseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VoteRecord extends BaseEntity {

	private Long voteRecordId;

	private Long voteId;

	private Long userId;

	private VoteType voteType;

	private Boolean isCanceled;

	private VoteRecord(LocalDateTime createdAt) {
		super(createdAt);
	}

	@Builder
	public VoteRecord(Long voteRecordId, Long voteId, Long userId, VoteType voteType, boolean isCanceled,
		LocalDateTime createdAt) {
		super(createdAt);
		this.voteRecordId = voteRecordId;
		this.voteId = voteId;
		this.userId = userId;
		this.voteType = voteType;
		this.isCanceled = isCanceled;
	}

	public void cancel() {
		this.isCanceled = true;
	}

}
