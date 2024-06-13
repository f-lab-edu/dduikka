package com.flab.dduikka.domain.vote.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.flab.dduikka.common.domain.Auditable;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VoteRecord extends Auditable {

	private Long voteRecordId;

	private Long voteId;

	private Long userId;

	private VoteType voteType;

	private Boolean isCanceled;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoteRecord that = (VoteRecord)o;
		return Objects.equals(voteRecordId, that.voteRecordId) && Objects.equals(voteId, that.voteId)
			&& Objects.equals(userId, that.userId) && voteType == that.voteType && Objects.equals(
			isCanceled, that.isCanceled);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voteRecordId, voteId, userId, voteType, isCanceled);
	}

	public void cancel() {
		this.isCanceled = true;
	}

}
