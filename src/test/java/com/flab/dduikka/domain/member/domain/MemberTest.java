package com.flab.dduikka.domain.member.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
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
	@DisplayName("비밀번호가 일치하면 true를 반환한다")
	void whenCheckPasswordThenReturnsTrue() {
		Member member = Member.builder()
			.memberId(1L)
			.email("dduikka@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		boolean checkPassword = member.isCorrectPassword(member.getPassword());

		assertThat(checkPassword).isTrue();
	}

	@Test
	@DisplayName("비밀번호가 일치하지 않으면 false 반환한다")
	void whenCheckPasswordThenReturnsFalse() {
		Member member = Member.builder()
			.memberId(1L)
			.email("dduikka@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		String wrongPassword = "3456";

		boolean checkPassword = member.isCorrectPassword(wrongPassword);

		assertThat(checkPassword).isFalse();
	}

	@Test
	@DisplayName("이메일이 다르면 동등성 판별 시 false를 반환한다")
	void whenComparingWithDifferentEmailThenReturnsFalse() {
		Member aMember = Member.builder()
			.email("another@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		Member anotherMember = Member.builder()
			.email("dduikka@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		boolean isEquals = aMember.equals(anotherMember);
		assertThat(isEquals).isFalse();
	}

	@Test
	@DisplayName("탈퇴한 회원과 아닌 회원은 이메일이 같아도 동등성 판별 시 false를 반환한다")
	void whenComparingWithDifferentJoinDateThenReturnsFalse() {
		Member aMember = Member.builder()
			.email("dduikka@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now().minusDays(1))
			.createAt(LocalDateTime.now())
			.build();

		Member anotherMember = Member.builder()
			.email("dduikka2@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		boolean isEquals = aMember.equals(anotherMember);
		assertThat(isEquals).isFalse();
	}
}
