package com.flab.dduikka.domain.vote.domain;

import java.util.Arrays;
import java.util.List;

public enum VoteType {
	RUN("뛴다"),
	NORUN("안뛴다");

	private final String desc;

	VoteType(String desc) {
		this.desc = desc;
	}

	public static List<VoteType> findAll() {
		return Arrays.stream(VoteType.values())
			.toList();
	}

	public String getDesc() {
		return desc;
	}
}
