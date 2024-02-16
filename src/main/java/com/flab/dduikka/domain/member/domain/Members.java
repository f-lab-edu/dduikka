package com.flab.dduikka.domain.member.domain;

import java.util.List;

public class Members {
	private List<Member> memberList;

	public Members(List<Member> members) {
		this.memberList = members;
	}

	public boolean isDuplicatedEmail() {
		return memberList.stream()
			.filter(Member::isJoined)
			.anyMatch(member -> true);
	}
}
