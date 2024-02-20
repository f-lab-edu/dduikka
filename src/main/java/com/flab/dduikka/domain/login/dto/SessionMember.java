package com.flab.dduikka.domain.login.dto;

import com.flab.dduikka.domain.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionMember {
	private long memberId;
	private String email;

	public static SessionMember from(Member member) {
		return new SessionMember(
			member.getMemberId()
			, member.getEmail()
		);
	}
}
