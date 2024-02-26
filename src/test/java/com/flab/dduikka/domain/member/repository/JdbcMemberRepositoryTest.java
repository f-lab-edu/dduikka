package com.flab.dduikka.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.SpringBootRepositoryTestHelper;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

class JdbcMemberRepositoryTest extends SpringBootRepositoryTestHelper {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("등록된 아이디로 회원을 조회하면 회원이 조회된다")
	void whenFindByIdThenMemberFound() {
		//given
		long memberId = 1L;
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.net")
			.password("1234")
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.memberStatus(MemberStatus.LEAVE)
			.build();
		memberRepository.addMember(mockMember);

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
		Member mockMember = Member.builder()
			.memberId(1L)
			.email(email)
			.password("1234")
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.memberStatus(MemberStatus.JOIN)
			.build();
		memberRepository.addMember(mockMember);

		//when
		Optional<Member> foundMember = memberRepository.findByEmailAndMemberStatus(email);

		//then
		assertThat(foundMember).isPresent();
		assertThat(foundMember.orElse(null)).isEqualTo(mockMember);
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
		String email = "test@dduikka.net";
		Member mockMember = Member.builder()
			.memberId(1L)
			.email(email)
			.password("1234")
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.memberStatus(MemberStatus.LEAVE)
			.build();
		memberRepository.addMember(mockMember);

		//when
		Optional<Member> optionalMember = memberRepository.findByEmailAndMemberStatus(email);

		//then
		assertThat(optionalMember).isEmpty();
	}

	@Test
	@DisplayName("회원을 등록한다")
	void whenAddMemberThenReturnsMember() {
		//given
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test5@dduikka.net")
			.password("1234")
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.memberStatus(MemberStatus.JOIN)
			.build();

		//when
		Member createdMember = memberRepository.addMember(mockMember);

		//then
		assertThat(createdMember).isEqualTo(mockMember);
	}

	@Test
	@DisplayName("회원이 탈퇴를 요청하면 회원상태를 변경한다")
	void whenLeaveMemberThenChangesMemberStatusAndJoinDate() {
		//given
		Member member = Member.builder()
			.memberId(1L)
			.email("test@dduikka.net")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now().minusDays(1))
			.createAt(LocalDateTime.now())
			.build();
		Member createdMember = memberRepository.addMember(member);
		createdMember.leave();

		//when
		memberRepository.leaveMember(createdMember);
		Optional<Member> optionalMember = memberRepository.findById(member.getMemberId());

		Member foundMember = optionalMember.orElse(null);
		assert foundMember != null;
		assertThat(foundMember.isJoined()).isFalse();
		assertThat(foundMember.getLeaveDate()).isNotNull();
	}
}
