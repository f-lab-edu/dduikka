package com.flab.dduikka.domain.vote.dto;

import java.time.LocalDate;

import com.flab.dduikka.domain.vote.domain.Vote;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteResponseDto {
	private Long voteId;
	private LocalDate voteDate;

	public static VoteResponseDto toDto(Vote vote) {
		return new VoteResponseDto(
			vote.getVoteId()
			, vote.getVoteDate());
	}
}
