package com.flab.dduikka.domain.member.repository;

import java.util.List;
import java.util.Optional;

import com.flab.dduikka.domain.member.domain.Member;

public interface MemberRepository {
	Optional<Member> findById(long userId);

	List<Member> findAllByEmail(String email);
}
