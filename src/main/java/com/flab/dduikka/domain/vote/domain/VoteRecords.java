package com.flab.dduikka.domain.vote.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class VoteRecords {

	private List<VoteRecord> voteRecordList;

	public VoteRecords(List<VoteRecord> voteRecordList) {
		this.voteRecordList = voteRecordList;
	}

	public Map<VoteType, Integer> countsVoteRecords() {
		Map<VoteType, Integer> voteTypeCountMap = new EnumMap<>(VoteType.class);
		for (VoteType voteType : VoteType.values()) {
			voteTypeCountMap.put(voteType, 0);
		}
		for (VoteRecord voteRecord : voteRecordList) {
			voteTypeCountMap.put(voteRecord.getVoteType(),
				voteTypeCountMap.get(voteRecord.getVoteType()) + 1);
		}
		return voteTypeCountMap;
	}
}
