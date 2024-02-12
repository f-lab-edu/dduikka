package com.flab.dduikka.domain.member.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class MemberTest {
	@Test
	void 가입한_회원이면_true를_반환한다() {
		Member member = Member.builder()
			.memberId(1L)
			.email("dduikka@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		boolean isJoined = member.isJoined();

		assertThat(isJoined).isTrue();
	}

	@Test
	void 탈퇴한_회원이면_false를_반환한다() {
		Member member = Member.builder()
			.memberId(1L)
			.email("dduikka@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		boolean isJoined = member.isJoined();

		assertThat(isJoined).isFalse();
	}
}
