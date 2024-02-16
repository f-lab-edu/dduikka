package com.flab.dduikka.domain.member.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MembersTest {

	@Test
	@DisplayName("탈퇴하지 않은 회원들의 이메일을 중복검사하면 true를 반환한다")
	void whenIsDuplicatedByEmailThenReturnsTrue() {
		//given
		Member member = Member.builder()
			.memberId(1L)
			.email("test@dduikka.net")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		List<Member> memberList = List.of(member);
		Members members = new Members(memberList);

		//when
		boolean isDuplicated = members.isDuplicatedEmail();

		//then
		assertThat(isDuplicated).isTrue();
	}

	@Test
	@DisplayName("탈퇴한 회원의 이메일을 중복검사하면 false를 반환한다")
	void whenIsDuplicatedByEmailOfLeftMemberThenReturnsFalse() {
		//given
		Member member = Member.builder()
			.memberId(1L)
			.email("test@dduikka.net")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		List<Member> memberList = List.of();
		Members members = new Members(memberList);

		//when
		boolean isDuplicated = members.isDuplicatedEmail();

		//then
		assertThat(isDuplicated).isFalse();
	}

	@Test
	@DisplayName("회원 리스트가 empty이고 이메일 중복 검사를하면 false를 반환한다")
	void whenMembersAreEmptyAndIsNotDuplicatedByEmailThenReturnsFalse() {
		//given
		List<Member> memberList = List.of();
		Members members = new Members(memberList);

		//when
		boolean isDuplicated = members.isDuplicatedEmail();

		//then
		assertThat(isDuplicated).isFalse();
	}

}
