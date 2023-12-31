package com.flab.dduikka.vote;

import java.util.Date;
import java.util.List;

import com.flab.dduikka.User;

// TODO 테스트 코드로 변경하기
public class VoteClientTest {
	public static void main(String[] args) {

		// 투표를 진행할 임시 유저를 생성한다.
		User user1 = new User("kimzoo2");
		User user2 = new User("marathonlover2");

		// 유저가 "뛴다"에 투표한다.
		Vote aVote = new Vote(new Date(202311228));
		VoteContentManager aVoteContentManager = new VoteContentManager(VoteContent.RUN);
		aVote.addVote(user1, aVoteContentManager);

		// 오늘 투표한 내역을 조회한다.
		Vote foundVote = aVote.findVoteByDate(new Date(202311228));

		// 오늘의 투표 컨텐츠 내역들을 조회한다.
		List<VoteContentManager> voteContentManagerList = aVote.findVoteContents();
		for (VoteContentManager voteContentManager : voteContentManagerList) {
			System.out.println(voteContentManager);
		}

		// test2 유저가 투표를 취소한다.
		aVote.cancelVote(user1, aVoteContentManager);
		for (VoteContentManager voteContentManager : voteContentManagerList) {
			System.out.println(voteContentManager);
		}

		// 다른 유저들이 "안 뛴다"에 투표한다.
		VoteContentManager anotherVoteContentManager = new VoteContentManager(VoteContent.NORUN);
		aVote.addVote(user2, anotherVoteContentManager);

		voteContentManagerList = aVote.findVoteContents();
		for (VoteContentManager voteContentManager : voteContentManagerList) {
			System.out.println(voteContentManager);
		}

		// 유저들이 투표한 기록을 조회한다.
		List<VoteRecord> voteRecordList = aVote.findVoteRecords();
		for (VoteRecord voteRecord : voteRecordList) {
			System.out.println(voteRecord);
		}
	}

}
