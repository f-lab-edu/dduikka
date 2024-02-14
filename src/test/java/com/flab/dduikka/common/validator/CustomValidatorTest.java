package com.flab.dduikka.common.validator;

import static org.assertj.core.api.BDDAssertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class CustomValidatorTest {

	private static final String notNullErrorMessage = "널이어서는 안됩니다.";
	private static final String notBlankErrorMessage = "공백일 수 없습니다.";
	private static final String emailErrorMessage = "올바른 형식의 이메일 주소여야 합니다.";
	private static final String pastOrPresentErrorMessage = "과거 또는 현재의 날짜여야 합니다.";

	private CustomValidator customValidator;

	@BeforeEach
	void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		customValidator = new CustomValidator(validator);
	}

	@Test
	void 객체의_유효성을_검증한다() {
		//given
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.LEAVE)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		//when
		customValidator.validateObject(mockMember);

		//then
	}

	@Test
	void notBlank인_필드에_null이나_공백이_들어가면_예외를_발생한다() {
		//given
		Member mockMember = Member.builder()
			.memberId(1L)
			// .email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
		Member anotherMember = Member.builder()
			.memberId(2L)
			.email("")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(mockMember))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(notBlankErrorMessage);
		assertThatThrownBy(() -> customValidator.validateObject(anotherMember))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(notBlankErrorMessage);
	}

	@Test
	void notNull인_필드에_null이_들어가면_예외를_발생한다() {
		//given
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.com")
			.password("1234")
			// .memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(mockMember))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(notNullErrorMessage);
	}

	@Test
	void email_형식이_알맞지_않으면_예외를_발생한다() {
		//given
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(mockMember))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(emailErrorMessage);
	}

	@Test
	void 과거나_현재날짜만_허용되는_필드가_미래일자면_예외를_발생한다() {
		//given
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		Member mockMember = Member.builder()
			.memberId(1L)
			.email("test@dduikka.com")
			.password("1234")
			.memberStatus(MemberStatus.JOIN)
			.joinDate(tomorrow)
			.createAt(LocalDateTime.now())
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(mockMember))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(pastOrPresentErrorMessage);
	}
}
