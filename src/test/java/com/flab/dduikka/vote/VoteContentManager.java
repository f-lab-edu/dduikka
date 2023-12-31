package com.flab.dduikka.vote;

import java.util.Objects;

public class VoteContentManager {
	private final VoteContent voteContent;
	private int voteCount;

	public VoteContentManager(VoteContent voteContent) {
		this.voteContent = voteContent;
		this.voteCount = 0;
	}

	public void addVoteContent() {
		increaseCount();
	}

	public void cancelVoteContent() {
		decreaseCount();
	}

	public String getVoteContent() {
		return voteContent.getDescription();
	}

	@Override
	public String toString() {
		return "VoteContent{" +
			"voteContent='" + voteContent.getDescription() + '\'' +
			", voteCount=" + voteCount +
			'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoteContentManager that = (VoteContentManager)o;
		return Objects.equals(voteContent, that.voteContent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voteContent);
	}

	private void increaseCount() {
		this.voteCount++;
	}

	private void decreaseCount() {
		if (voteCount <= 0) {
			System.out.println("투표수가 0 이하입니다. 투표를 취소할 수 없습니다.");
			return;
		}
		this.voteCount--;
	}
}
