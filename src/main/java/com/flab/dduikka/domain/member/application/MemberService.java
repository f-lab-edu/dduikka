package com.flab.dduikka.domain.member.application;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;
import com.flab.dduikka.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberResponseDto findMember(final long memberId) {
		Member foundUser = memberRepository.findByIdAndMemberStatus(memberId, MemberStatus.JOIN)
			.orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다. userId: " + memberId));
		return MemberResponseDto.toDto(foundUser);
	}
}
