package com.flab.dduikka.vote;

import java.util.Objects;

import com.flab.dduikka.User;

public class VoteRecord {
	private User user;

	private Vote vote;
	private VoteContent voteContent;

	private boolean isCanceled;

	public VoteRecord(User user, Vote vote, VoteContent voteContent) {
		this.user = user;
		this.vote = vote;
		this.voteContent = voteContent;
		this.isCanceled = false;
	}

	public void addVoteRecord(User user, VoteContent voteContent) {
		System.out.println(user.toString() + "님이 " + voteContent.getVoteContent() + "에 투표 하셨습니다.");
	}

	public void deleteVoteRecord() {
		this.isCanceled = true;
		System.out.println(user.toString() + "님이 " + voteContent.getVoteContent() + "에 한 투표를 취소하셨습니다.");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VoteRecord that = (VoteRecord)o;
		return Objects.equals(user, that.user) && Objects.equals(voteContent, that.voteContent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, voteContent);
	}

	@Override
	public String toString() {
		return "VoteRecord{" +
			"user=" + user +
			", vote=" + vote +
			", voteContent=" + voteContent +
			", isCanceled=" + isCanceled +
			'}';
	}
}
