package com.flab.dduikka.domain.member.dto;

import java.time.LocalDate;

import com.flab.dduikka.domain.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

	private final Long memberId;
	private final String email;
	private final LocalDate joinDate;

	public static MemberResponseDto from(Member member) {
		return new MemberResponseDto(
			member.getMemberId(),
			member.getEmail(),
			member.getJoinDate()
		);
	}
}
