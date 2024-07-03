package com.flab.dduikka.domain.vote.dto;

import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteRecordResponseDTO {

	private long voteRecordId;
	private long voteId;
	private VoteType voteType;

	public VoteRecordResponseDTO(long voteId) {
		this.voteId = voteId;
	}

	public static VoteRecordResponseDTO toDto(VoteRecord voteRecord) {
		return new VoteRecordResponseDTO(
			voteRecord.getVoteId()
			, voteRecord.getVoteRecordId()
			, voteRecord.getVoteType()
		);
	}
}
