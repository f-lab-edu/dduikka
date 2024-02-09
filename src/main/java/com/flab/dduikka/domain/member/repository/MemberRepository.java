package com.flab.dduikka.domain.member.repository;

import java.util.Optional;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

public interface MemberRepository {
	Optional<Member> findByIdAndMemberStatus(long userId, MemberStatus memberStatus);
}
