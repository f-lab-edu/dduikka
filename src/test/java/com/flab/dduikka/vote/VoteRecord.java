package com.flab.dduikka.vote;

import java.util.Objects;

import com.flab.dduikka.User;

public class VoteRecord {
	private User user;
	private Vote vote;
	private VoteType voteType;
	private boolean isCanceled;

	public VoteRecord(User user, Vote vote, VoteType voteType) {
		this.user = user;
		this.vote = vote;
		this.voteType = voteType;
		this.isCanceled = false;
	}

	public void addVoteRecord(User user, VoteType voteType) {
		System.out.println(user.toString() + "님이 " + voteType.getDescription() + "에 투표하셨습니다.");
	}

	public void deleteVoteRecord() {
		this.isCanceled = true;
		System.out.println(user.toString() + "님이 " + voteType.getDescription() + "에 한 투표를 취소하셨습니다.");
	}

	public VoteRecord findVoteRecordByUserAndVote(User user, VoteType voteType) {
		if (isCanceled)
			return null;
		if (!this.user.equals(user) || !this.voteType.equals(voteType))
			return null;

		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoteRecord that = (VoteRecord)o;
		return Objects.equals(user, that.user) && Objects.equals(vote, that.vote)
			&& voteType == that.voteType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, vote, voteType);
	}

	@Override
	public String toString() {
		return "VoteRecord{" +
			"user=" + user +
			", vote=" + vote +
			", isCanceled=" + isCanceled +
			'}';
	}
}
