package com.flab.dduikka.domain.vote.dto;

import com.flab.dduikka.domain.vote.domain.VoteType;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class VoteRecordAddRequestDto {

	@NotBlank(message = "voteId null 일 수 없습니다.")
	private final long voteId;
	@NotBlank(message = "userId null 일 수 없습니다.")
	private final long userId;
	@NotBlank(message = "voteType null 일 수 없습니다.")
	private final VoteType voteType;
	private final boolean isCanceled;

	public VoteRecordAddRequestDto(long voteId, long userId, VoteType voteType) {
		this.voteId = voteId;
		this.userId = userId;
		this.voteType = voteType;
		this.isCanceled = false;
	}
}
