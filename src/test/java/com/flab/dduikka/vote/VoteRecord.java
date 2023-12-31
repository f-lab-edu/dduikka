package com.flab.dduikka.vote;

import java.util.Objects;

import com.flab.dduikka.User;

public class VoteRecord {
	private User user;
	private Vote vote;
	private VoteContentManager voteContentManager;

	private boolean isCanceled;

	public VoteRecord(User user, Vote vote, VoteContentManager voteContentManager) {
		this.user = user;
		this.vote = vote;
		this.voteContentManager = voteContentManager;
		this.isCanceled = false;
	}

	public void addVoteRecord(User user, VoteContentManager voteContentManager) {
		System.out.println(user.toString() + "님이 " + voteContentManager.getVoteContent() + "에 한 투표를 취소하셨습니다.");
	}

	public void deleteVoteRecord() {
		this.isCanceled = true;
		System.out.println(user.toString() + "님이 " + voteContentManager.getVoteContent() + "에 한 투표를 취소하셨습니다.");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoteRecord that = (VoteRecord)o;
		return Objects.equals(user, that.user) && Objects.equals(voteContentManager, that.voteContentManager);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, voteContentManager);
	}

	@Override
	public String toString() {
		return "VoteRecord{" +
			"user=" + user +
			", vote=" + vote +
			", voteContent=" + voteContentManager +
			", isCanceled=" + isCanceled +
			'}';
	}
}
