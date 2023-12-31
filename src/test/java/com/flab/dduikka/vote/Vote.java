package com.flab.dduikka.vote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flab.dduikka.User;

public class Vote {

	private final Date date;
	private Set<VoteContent> voteContentList = new HashSet<>();
	private List<VoteRecord> voteRecordList = new ArrayList<>();

	public Vote(Date date) {
		this.date = date;
	}

	public void addVote(User user, VoteContent voteContent) {
		voteContent.addVoteContent();
		addVoteContent(voteContent);

		VoteRecord voteRecord = new VoteRecord(user, this, voteContent);
		voteRecord.addVoteRecord(user, voteContent);
		addVoteRecord(voteRecord);
	}

	public Vote findVoteByDate(Date date) {
		if (this.date.compareTo(date) == 0)
			return this;
		return null;
	}

	public void cancelVote(User user, VoteContent voteContent) {
		voteContent.cancelVoteContent();
		VoteRecord voteRecord = findVoteRecordByUserAndVoteContent(user, voteContent);
		if (voteRecord == null) {
			return;
		}
		voteRecord.deleteVoteRecord();
	}

	private void addVoteContent(VoteContent voteContent) {
		this.voteContentList.add(voteContent);
	}

	private void addVoteRecord(VoteRecord voteRecord) {
		voteRecordList.add(voteRecord);
	}

	private VoteRecord findVoteRecordByUserAndVoteContent(User user, VoteContent voteContent) {
		VoteRecord aVoteRecord = new VoteRecord(user, this, voteContent);
		for (VoteRecord voteRecord : voteRecordList) {
			if (voteRecord.equals(aVoteRecord)) {
				return voteRecord;
			}
		}
		System.out.println("기록된 투표가 아닙니다.");
		return null;
	}

	public List<VoteContent> findVoteContents() {
		return new ArrayList<>(voteContentList);
	}

	public List<VoteRecord> findVoteRecords() {
		return new ArrayList<>(voteRecordList);
	}

	@Override
	public String toString() {
		return "Vote{" +
			"date=" + date +
			'}';
	}
}
