package com.flab.dduikka.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

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
	@DisplayName("등록된 회원의 이메일로 조회하면 true가 반환된다")
	void whenNotExistsByEmailThenReturnsTure() {
		//given
		String email = "test@dduikka.net";

		//when
		boolean existsByEmail = memberRepository.existsByEmailAndMemberStatus(email);

		//then
		assertThat(existsByEmail).isTrue();
	}

	@Test
	@DisplayName("등록된 회원의 이메일로 조회하면 false가 반환된다")
	void whenExistsByEmailThenReturnsFalse() {
		//given
		String email = "email@dduikka.net";

		//when
		boolean existsByEmail = memberRepository.existsByEmailAndMemberStatus(email);

		//then
		assertThat(existsByEmail).isFalse();
	}
}
