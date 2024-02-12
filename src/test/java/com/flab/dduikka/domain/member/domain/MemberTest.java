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

	@Test
	void 가입일자가_미래면_예외를_발생시킨다() {
		LocalDate tomorrow = LocalDate.now().plusDays(10);

		assertThatThrownBy(
			() -> Member.builder()
				.memberId(1L)
				.email("dduikka@dduikka.com")
				.password("1234")
				.memberStatus(MemberStatus.JOIN)
				.joinDate(tomorrow)
				.createAt(LocalDateTime.now())
				.build()
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("%s는 미래날짜일 수 없습니다.".formatted("joinDate"));
	}

	@Test
	void 이메일을_입력하지_않으면_예외가_발생한다() {
		assertThatThrownBy(
			() -> Member.builder()
				.memberId(1L)
				// .email("dduikka@dduikka.com")
				.password("1234")
				.memberStatus(MemberStatus.JOIN)
				.joinDate(LocalDate.now())
				.createAt(LocalDateTime.now())
				.build()
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("%s는 null 또는 공백일 수 없습니다.".formatted("email"));
	}

	@Test
	void 비밀번호를_입력하지_않으면_예외가_발생한다() {
		assertThatThrownBy(
			() -> Member.builder()
				.memberId(1L)
				.email("dduikka@dduikka.com")
				// .password("1234")
				.memberStatus(MemberStatus.JOIN)
				.joinDate(LocalDate.now())
				.createAt(LocalDateTime.now())
				.build()
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("%s는 null 또는 공백일 수 없습니다.".formatted("password"));
	}

	@Test
	void 멤버_상태를_입력하지_않으면_예외가_발생한다() {
		assertThatThrownBy(
			() -> Member.builder()
				.memberId(1L)
				.email("dduikka@dduikka.com")
				.password("1234")
				// .memberStatus(MemberStatus.JOIN)
				.joinDate(LocalDate.now())
				.createAt(LocalDateTime.now())
				.build()
		).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("%s는 null일 수 없습니다.".formatted("memberStatus"));
	}

}
