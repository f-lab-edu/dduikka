package com.flab.dduikka.domain.vote.domain;

public enum VoteType {
	RUN("뛴다"),
	NORUN("안뛴다");

	private final String desc;

	VoteType(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
