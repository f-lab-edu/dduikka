package com.flab.dduikka.vote;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.flab.dduikka.User;

public class Vote {

	private final Date date;
	private Set<VoteContentManager> voteContentManagerList = new HashSet<>();
	private List<VoteRecord> voteRecordList = new ArrayList<>();

	public Vote(Date date) {
		this.date = date;
	}

	public void addVote(User user, VoteContentManager voteContentManager) {
		voteContentManager.addVoteContent();
		addVoteContent(voteContentManager);

		VoteRecord voteRecord = new VoteRecord(user, this, voteContentManager);
		voteRecord.addVoteRecord(user, voteContentManager);
		addVoteRecord(voteRecord);
	}

	public Vote findVoteByDate(Date date) {
		if (this.date.compareTo(date) == 0)
			return this;
		return null;
	}

	public void cancelVote(User user, VoteContentManager voteContentManager) {
		voteContentManager.cancelVoteContent();
		VoteRecord voteRecord = findVoteRecordByUserAndVoteContent(user, voteContentManager);
		if (voteRecord == null) {
			return;
		}
		voteRecord.deleteVoteRecord();
	}

	private void addVoteContent(VoteContentManager voteContentManager) {
		this.voteContentManagerList.add(voteContentManager);
	}

	private void addVoteRecord(VoteRecord voteRecord) {
		voteRecordList.add(voteRecord);
	}

	private VoteRecord findVoteRecordByUserAndVoteContent(User user, VoteContentManager voteContentManager) {
		VoteRecord aVoteRecord = new VoteRecord(user, this, voteContentManager);
		for (VoteRecord voteRecord : voteRecordList) {
			if (voteRecord.equals(aVoteRecord)) {
				return voteRecord;
			}
		}
		System.out.println("기록된 투표가 아닙니다.");
		return null;
	}

	public List<VoteContentManager> findVoteContents() {
		return new ArrayList<>(voteContentManagerList);
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
