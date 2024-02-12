package com.flab.dduikka.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.SpringBootRepositoryTestHelper;
import com.flab.dduikka.domain.member.domain.Member;

class JdbcMemberRepositoryTest extends SpringBootRepositoryTestHelper {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void 회원을_정상_조회한다() {
		//given
		long memberId = 1L;
		// TODO 회원가입 기능 추가 후 memberRepository.save 추가한다.

		//when
		Member foundMember = memberRepository.findById(memberId).get();

		//then
		assertThat(foundMember.getMemberId()).isEqualTo(memberId);
	}
}
