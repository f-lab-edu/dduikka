package com.flab.dduikka.domain.vote.dto;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class VoteRecordAddRequestDTO {

	@NotNull(message = "voteId null 일 수 없습니다.")
	private final long voteId;
	@NotNull(message = "userId null 일 수 없습니다.")
	private final long userId;
	@NotNull(message = "voteType null 일 수 없습니다.")
	private final VoteType voteType;
	private final boolean canceledFlag;

	public VoteRecordAddRequestDTO(long voteId, long userId, VoteType voteType) {
		this.voteId = voteId;
		this.userId = userId;
		this.voteType = voteType;
		this.canceledFlag = false;
	}

	public static VoteRecord toEntity(VoteRecordAddRequestDTO request) {
		return VoteRecord.builder()
			.voteId(request.getVoteId())
			.memberId(request.getUserId())
			.voteType(request.getVoteType())
			.canceledFlag(request.isCanceledFlag())
			.createdAt(LocalDateTime.now())
			.build();
	}
}
