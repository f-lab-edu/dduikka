package com.flab.dduikka.vote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.flab.dduikka.User;

public class Vote {

	private final LocalDate date;
	private Map<VoteType, Integer> voteCounts = new EnumMap<>(VoteType.class);
	private List<VoteRecord> voteRecordList = new ArrayList<>();

	public Vote(LocalDate date) {
		this.date = date;
	}

	public void addVote(User user, VoteType voteType, VoteRecord voteRecord) {
		voteCounts.put(voteType, voteCounts.getOrDefault(voteType, 0) + 1);
		voteRecord.addVoteRecord(user, voteType);
		addVoteRecord(voteRecord);
	}

	public Vote findVoteByDate(LocalDate date) {
		if (!this.date.isEqual(date))
			return null;
		return this;
	}

	public void cancelVote(VoteType voteType, VoteRecord voteRecord) {
		voteCounts.computeIfPresent(voteType, (k, v) -> v - 1);
		voteRecordList.remove(voteRecord);
		voteRecord.deleteVoteRecord();
	}

	private void addVoteRecord(VoteRecord voteRecord) {
		voteRecordList.add(voteRecord);
	}

	public int getVoteContentCount(VoteType voteType) {
		return voteCounts.get(voteType);
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
