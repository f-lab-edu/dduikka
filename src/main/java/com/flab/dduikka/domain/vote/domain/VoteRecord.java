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

	private Long memberId;

	private VoteType voteType;

	private Boolean canceledFlag;

	@Builder
	public VoteRecord(Long voteRecordId, Long voteId, Long memberId, VoteType voteType, boolean canceledFlag,
		LocalDateTime createdAt) {
		super(createdAt);
		this.voteRecordId = voteRecordId;
		this.voteId = voteId;
		this.memberId = memberId;
		this.voteType = voteType;
		this.canceledFlag = canceledFlag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoteRecord that = (VoteRecord)o;
		return Objects.equals(voteRecordId, that.voteRecordId) && Objects.equals(voteId, that.voteId)
			&& Objects.equals(memberId, that.memberId) && voteType == that.voteType && Objects.equals(
			canceledFlag, that.canceledFlag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voteRecordId, voteId, memberId, voteType, canceledFlag);
	}

	public void cancel() {
		this.canceledFlag = true;
	}

}
