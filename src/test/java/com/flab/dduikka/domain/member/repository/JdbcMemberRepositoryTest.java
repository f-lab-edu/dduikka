package com.flab.dduikka.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.SpringBootRepositoryTestHelper;
import com.flab.dduikka.domain.member.domain.Member;

class JdbcMemberRepositoryTest extends SpringBootRepositoryTestHelper {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("등록된 아이디로 회원을 조회하면 회원이 조회된다")
	void whenFindByIdThenMemberFound() {
		//given
		long memberId = 1L;
		// TODO 회원가입 기능 추가 후 memberRepository.save 추가한다.

		//when
		Member foundMember = memberRepository.findById(memberId).get();

		//then
		assertThat(foundMember.getMemberId()).isEqualTo(memberId);
	}

	@Test
	@DisplayName("등록된 회원의 이메일을 조회하면 회원을 반환한다")
	void whenFindByEmailThenMemberFound() {
		//given
		String email = "test@dduikka.net";

		//when
		Optional<Member> foundMember = memberRepository.findByEmailAndMemberStatus(email);

		//then
		assertThat(foundMember).isPresent();
	}

	@Test
	@DisplayName("등록되지 않은 회원의 이메일을 조회하면 empty를 반환한다")
	void whenFindByEmailOfUnregisteredMemberThenResultIsEmpty() {
		//given
		String email = "email@dduikka.net";

		//when
		Optional<Member> optionalMember = memberRepository.findByEmailAndMemberStatus(email);

		//then
		assertThat(optionalMember).isEmpty();
	}

	@Test
	@DisplayName("탈퇴한 회원의 이메일을 조회하면 empty를 반환한다")
	void whenFindByEmailOfLeavedMemberThenResultIsEmpty() {
		//given
		String email = "test2@dduikka.net";

		//when
		Optional<Member> optionalMember = memberRepository.findByEmailAndMemberStatus(email);

		//then
		assertThat(optionalMember).isEmpty();
	}
}
