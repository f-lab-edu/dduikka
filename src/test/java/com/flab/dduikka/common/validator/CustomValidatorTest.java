package com.flab.dduikka.common.validator;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@ExtendWith(MockitoExtension.class)
class CustomValidatorTest {
	@InjectMocks
	private CustomValidator customValidator;
	@Mock
	private Validator validator;

	@Test
	void 객체의_유효성을_검증한다() {
		//given
		long memberId = 1L;
		Set<ConstraintViolation<Object>> mockViolation
			= Collections.emptySet();
		Member mockMember = Member.builder()
			.memberId(memberId)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		given(validator.validate(any())).willReturn(mockViolation);

		//when
		customValidator.validateObject(mockMember);

		//then
		verify(validator, times(1)).validate(mockMember);
	}

	@Test
	void 객체의_유효성_검증이_실패하면_예외를_발생한다() {
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
		given(validator.validate(any())).willThrow(IllegalStateException.class);

		//when, then
		thenThrownBy(() -> customValidator.validateObject(mockMember))
			.isInstanceOf(IllegalStateException.class);
	}
}
