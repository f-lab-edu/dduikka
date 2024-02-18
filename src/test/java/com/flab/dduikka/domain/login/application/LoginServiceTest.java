package com.flab.dduikka.domain.login.application;

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
import com.flab.dduikka.domain.login.dto.LoginRequestDto;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;
import com.flab.dduikka.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

	@InjectMocks
	private LoginService loginService;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private CustomValidator validator;

	@Test
	@DisplayName("로그인하면 회원을 반환한다.")
	void whenMemberRegisteredThenMembersReturn() {
		String email = "test@dduikka.com";
		Member mockMember =
			Member.builder()
				.memberId(1L)
				.password("1234")
				.email("test@dduikka.com")
				.memberStatus(MemberStatus.JOIN)
				.joinDate(LocalDate.now())
				.createAt(LocalDateTime.now())
				.build();
		LoginRequestDto request = new LoginRequestDto(email, "1234");

		given(memberRepository.findByEmailAndMemberStatus(email)).willReturn(Optional.ofNullable(mockMember));

		SessionMember sessionMember = loginService.login(request);
		assert mockMember != null;
		then(sessionMember.getMemberId()).isEqualTo(mockMember.getMemberId());
		then(sessionMember.getEmail()).isEqualTo(mockMember.getEmail());
	}

	@Test
	@DisplayName("가입하지 않은 회원이 로그인하면 NoSuchElementException 예외를 발생시킨다.")
	void whenUnregisteredMemberLogInThenThrowNoSuchElementException() {
		String email = "test@dduikka.com";
		LoginRequestDto request = new LoginRequestDto(email, "1234");
		given(memberRepository.findByEmailAndMemberStatus(request.getEmail()))
			.willReturn(Optional.empty());

		thenThrownBy(
			() -> loginService.login(request))
			.isInstanceOf(NoSuchElementException.class)
			.hasMessageContaining("해당 회원이 존재하지 않습니다.");
	}

	@Test
	@DisplayName("비밀번호가 틀리면 IllegalStateException 예외를 발생시킨다.")
	void whenPasswordIsIncorrectThenThrowIllegalStateException() {
		String email = "test@dduikka.com";
		LoginRequestDto request = new LoginRequestDto(email, "wrongPassword");
		Member mockMember = Member.builder()
			.memberId(1L)
			.email(email)
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.findByEmailAndMemberStatus(email))
			.willReturn(Optional.ofNullable(mockMember));

		thenThrownBy(
			() -> loginService.login(request))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("이메일 혹은 패스워드가 맞지 않습니다.");
	}
}
