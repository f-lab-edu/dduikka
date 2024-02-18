package com.flab.dduikka.domain.member.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;
import com.flab.dduikka.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private CustomValidator validator;

	@Test
	void 회원_가입을_하면_유저가_등록된다() {
		// given
		long memberId = 1L;
		Member mockMember = Member.builder()
			.memberId(memberId)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.findById(memberId))
			.willReturn(Optional.ofNullable(mockMember));

		// when
		MemberResponseDto response = memberService.findMember(memberId);

		//then
		then(response.getMemberId()).isEqualTo(memberId);
		verify(memberRepository, times(1)).findById(memberId);
	}

	@Test
	void 등록되지_않은_회원을_조회하면_예외가_발생한다() {
		//given
		long memberId = 1L;
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.empty());

		//when,then
		thenThrownBy(
			() -> memberService.findMember(memberId)).isInstanceOf(NoSuchElementException.class);
	}

	@Test
	void 탈퇴한_회원을_조회하면_예외가_발생한다() {
		//given
		long memberId = 1L;
		Member mockMember = Member.builder()
			.memberId(memberId)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.ofNullable(mockMember));

		//when,then
		thenThrownBy(
			() -> memberService.findMember(memberId)).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void 조회한_멤버_객체_유효성_검증을_실패하면_예외가_발생한다() {
		//given
		long memberId = 1L;
		Member mockMember = Member.builder()
			.memberId(memberId)
			// .email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.ofNullable(mockMember));
		doThrow(IllegalStateException.class).when(validator).validateObject(any());

		//when,then
		thenThrownBy(
			() -> memberService.findMember(memberId)).isInstanceOf(IllegalStateException.class);
	}

	@Test
	void 이메일이_중복되면_true를_반환한다() {
		//given
		String email = "test@dduikka.com";
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.existsByEmailAndMemberStatus(email))
			.willReturn(Boolean.TRUE);
		//when
		boolean response = memberService.isEmailDuplicated(email);
		//then
		assertThat(response).isTrue();
	}

	@Test
	@DisplayName("이메일이 중복되지 않으면 true를 반환한다")
	void whenIsNotEmailDuplicatedThenReturnsTrue() {
		//given
		String email = "test@dduikka.com";
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.existsByEmailAndMemberStatus(email))
			.willReturn(Boolean.FALSE);
		//when
		boolean response = memberService.isEmailDuplicated(email);
		//then
		assertThat(response).isFalse();
	}

	@Test
	@DisplayName("이메일이 중복되지 않으면 false를 반환한다")
	void whenIsNotEmailDuplicatedThenReturnsFalse() {
		//given
		String email = "test@dduikka.com";

		given(memberRepository.existsByEmailAndMemberStatus(email))
			.willReturn(Boolean.FALSE);
		//when
		boolean response = memberService.isEmailDuplicated(email);
		//then
		assertThat(response).isFalse();
	}

}
