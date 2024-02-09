package com.flab.dduikka.domain.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

	@Test
	void 회원_가입을_하면_유저가_등록된다() {
		// given
		long memberId = 1L;
		Member mockUser = Member.builder()
			.memberId(memberId)
			.email("test@dduikka.com")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(memberRepository.findByIdAndMemberStatus(anyLong(), any()))
			.willReturn(Optional.ofNullable(mockUser));

		// when
		MemberResponseDto response = memberService.findMember(memberId);

		//then
		assertThat(response.getMemberId()).isEqualTo(memberId);
	}

	@Test
	void 등록되지_않은_회원을_조회하면_예외가_발생한다() {
		//given
		long memberId = 1L;
		given(memberRepository.findByIdAndMemberStatus(anyLong(), any()))
			.willReturn(Optional.empty());

		//when,then
		assertThrows(NoSuchElementException.class, () -> memberService.findMember(memberId));
	}
}
