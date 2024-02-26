package com.flab.dduikka.domain.member.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;
import com.flab.dduikka.domain.member.dto.MemberRegisterRequestDto;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;
import com.flab.dduikka.domain.member.exception.MemberException;
import com.flab.dduikka.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private CustomValidator validator;

	@BeforeEach
	void setUp() {
		memberService = new MemberService(
			"^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W]).{10,}$",
			memberRepository,
			validator
		);
	}

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
			() -> memberService.findMember(memberId)).isInstanceOf(MemberException.NotFoundMemberException.class);
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
		given(memberRepository.findByEmailAndMemberStatus(email))
			.willReturn(Optional.ofNullable(mockMember));
		//when
		boolean response = memberService.isEmailDuplicated(email);
		//then
		assertThat(response).isTrue();
	}

	@Test
	@DisplayName("이메일이 중복되지 않으면 false를 반환한다")
	void whenIsNotEmailDuplicatedThenReturnsFalse() {
		//given
		String email = "test@dduikka.com";

		given(memberRepository.findByEmailAndMemberStatus(email))
			.willReturn(Optional.empty());
		//when
		boolean response = memberService.isEmailDuplicated(email);
		//then
		assertThat(response).isFalse();
	}

	@Test
	@DisplayName("회원가입 시 비밀번호 조건을 충족하지 못하면 예외를 반환한다")
	void whenRegisterMemberIllegalPasswordThenThrowsIllegalStateException() {
		//given
		MemberRegisterRequestDto request
			= new MemberRegisterRequestDto(
			"test@dduikka.net",
			"1234");
		//TODO: 수정
		//when, then
		thenThrownBy(
			() -> memberService.registerMember(request))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("비밀번호를 다시 입력해주세요.");
	}

	@Test
	@DisplayName("회원이 이미 등록된 이메일로 회원가입하면 예외를 반환한다")
	void whenRegisterMemberThenThrowsDuplicatedEmailException() {
		//given
		MemberRegisterRequestDto request
			= new MemberRegisterRequestDto(
			"test@dduikka.net",
			"123456qW!@");
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.net")
			.password("1234")
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.memberStatus(MemberStatus.JOIN)
			.build();
		//TODO: 수정

		given(memberRepository.findByEmailAndMemberStatus(anyString()))
			.willReturn(Optional.ofNullable(mockMember));

		//when,then
		thenThrownBy(
			() -> memberService.registerMember(request)
		).isInstanceOf(MemberException.DuplicatedEmailException.class)
			.hasMessageContaining("기등록된 회원입니다. email" + request.getEmail());
	}

	@Test
	@DisplayName("회원이 회원가입 요청 하면 addMember를 호출한다")
	void whenRegisterMemberThenCallAddMember() {
		//given
		MemberRegisterRequestDto request
			= new MemberRegisterRequestDto(
			"test@dduikka.net",
			"123456qW!@");
		//TODO: 수정
		Member newMember = MemberRegisterRequestDto.to(request);

		given(memberRepository.findByEmailAndMemberStatus(anyString()))
			.willReturn(Optional.empty());
		given(memberRepository.addMember(any()))
			.willReturn(newMember);

		//when
		memberService.registerMember(request);

		//then
		verify(memberRepository, times(1)).addMember(any(Member.class));
	}

	@Test
	@DisplayName("회원이 탈퇴하면 회원조회 되지 않는다")
	void whenLeaveMemberThenNotFoundMember() {
		//given
		long memberId = 1L;
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.ofNullable(mockMember))
			.willReturn(Optional.empty());

		//when
		memberService.leaveMember(memberId);

		//then
		thenThrownBy(() -> memberService.findMember(anyLong()))
			.isInstanceOf(MemberException.NotFoundMemberException.class)
			.hasMessageContaining("해당 유저가 존재하지 않습니다.");
	}

}
