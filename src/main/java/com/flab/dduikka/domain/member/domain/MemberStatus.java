package com.flab.dduikka.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatus {
	JOIN("회원가입"),
	LEAVE("탈퇴");

	private final String desc;
}
