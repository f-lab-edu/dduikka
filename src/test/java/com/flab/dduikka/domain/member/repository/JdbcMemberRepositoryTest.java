package com.flab.dduikka.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.SpringBootRepositoryTestHelper;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

// TODO 회원가입 기능 추가 후 save 추가한다.
class JdbcMemberRepositoryTest extends SpringBootRepositoryTestHelper {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	void 회원을_정상_조회한다() {
		//given
		long memberId = 1L;

		//when
		Member foundMember = memberRepository.findByIdAndMemberStatus(memberId, MemberStatus.JOIN).get();

		//then
		assertThat(foundMember.getMemberId()).isEqualTo(memberId);
	}

	@Test
	void 회원_탈퇴한_유저는_조회되지_않는다() {
		//given
		long memberId = 2L;

		//when
		Optional<Member> foundMember = memberRepository.findByIdAndMemberStatus(memberId, MemberStatus.JOIN);

		//then
		assertThat(foundMember).isEmpty();
	}
}
