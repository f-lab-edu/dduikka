package com.flab.dduikka.domain.member.application;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;
import com.flab.dduikka.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final CustomValidator validator;

	public MemberResponseDto findMember(final long memberId) {
		Member foundUser = memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다. userId: " + memberId));
		validator.validateObject(foundUser);
		if (!foundUser.isJoined()) {
			throw new IllegalStateException("탈퇴한 회원입니다. memberId:" + memberId);
		}
		return MemberResponseDto.from(foundUser);
	}

	public Boolean isEmailDuplicated(String email) {
		return memberRepository.existsByEmailAndMemberStatus(email);
	}
}
