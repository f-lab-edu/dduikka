package com.flab.dduikka.vote;

public enum VoteContent {
	RUN("뛴다"),
	NORUN("안 뛴다");

	private final String description;

	VoteContent(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
