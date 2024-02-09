package com.flab.dduikka.domain.member.dto;

import java.time.LocalDate;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

	private final Long memberId;
	private final String email;
	private final LocalDate joinDate;
	private final MemberStatus memberStatus;

	public static MemberResponseDto toDto(Member member) {
		return new MemberResponseDto(
			member.getMemberId(),
			member.getEmail(),
			member.getJoinDate(),
			member.getMemberStatus()
		);
	}
}
