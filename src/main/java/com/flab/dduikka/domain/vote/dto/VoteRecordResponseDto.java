package com.flab.dduikka.domain.vote.dto;

import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteRecordResponseDto {

	private long voteRecordId;
	private long voteId;
	private VoteType voteType;

	public VoteRecordResponseDto(long voteId) {
		this.voteId = voteId;
	}

	public static VoteRecordResponseDto toDto(VoteRecord voteRecord) {
		return new VoteRecordResponseDto(
			voteRecord.getVoteId()
			, voteRecord.getVoteRecordId()
			, voteRecord.getVoteType()
		);
	}
}
