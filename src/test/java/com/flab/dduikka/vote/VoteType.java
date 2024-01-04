package com.flab.dduikka.vote;

public enum VoteType {
	RUN("뛴다"),
	NORUN("안 뛴다");

	private final String description;

	VoteType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
