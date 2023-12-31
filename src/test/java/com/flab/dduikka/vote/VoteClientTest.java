package com.flab.dduikka.vote;

import java.util.Date;
import java.util.List;

import com.flab.dduikka.User;

// TODO 테스트 코드로 변경하기
public class VoteClientTest {
	public static void main(String[] args) {

		// 투표를 진행할 임시 유저를 생성한다.
		User user1 = new User("test2");
		User user2 = new User("toby33");

		// 유저가 "뛴다"에 투표한다.
		Vote aVote = new Vote(new Date(202311228));
		VoteContent aVoteContent = new VoteContent("뛴다");
		aVote.addVote(user1, aVoteContent);

		// 오늘 투표한 내역을 조회한다.
		Vote foundVote = aVote.findVoteByDate(new Date(202311228));

		// 오늘의 투표 컨텐츠 내역들을 조회한다.
		List<VoteContent> voteContentList = aVote.findVoteContents();
		for (VoteContent voteContent : voteContentList) {
			System.out.println(voteContent);
		}

		// test2 유저가 투표를 취소한다.
		aVote.cancelVote(user1, aVoteContent);
		for (VoteContent voteContent : voteContentList) {
			System.out.println(voteContent);
		}

		// 다른 유저들이 "안 뛴다"에 투표한다.
		VoteContent anotherVoteContent = new VoteContent("안뛴다");
		aVote.addVote(user2, anotherVoteContent);

		voteContentList = aVote.findVoteContents();
		for (VoteContent voteContent : voteContentList) {
			System.out.println(voteContent);
		}

		// 유저들이 투표한 기록을 조회한다.
		List<VoteRecord> voteRecordList = aVote.findVoteRecords();
		for (VoteRecord voteRecord : voteRecordList) {
			System.out.println(voteRecord);
		}
	}

}
