package com.flab.dduikka.domain.vote.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteType {
	RUN("뛴다"),
	NORUN("안뛴다");

	private final String desc;
}
