package com.flab.dduikka.domain.vote.dto;

import java.time.LocalDate;
import java.util.Map;

import com.flab.dduikka.domain.vote.domain.Vote;
import com.flab.dduikka.domain.vote.domain.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteResponseDTO {
	private Long voteId;
	private LocalDate voteDate;
	private Map<VoteType, Integer> voteTypeCountMap;

	public static VoteResponseDTO toDto(Vote vote, Map<VoteType, Integer> voteTypeCountMap) {
		return new VoteResponseDTO(
			vote.getVoteId()
			, vote.getVoteDate()
			, voteTypeCountMap)
			;
	}
}
