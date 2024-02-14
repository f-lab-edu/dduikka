package com.flab.dduikka.common.validator;

import static org.assertj.core.api.BDDAssertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class CustomValidatorTest {
	private CustomValidator customValidator;

	@BeforeEach
	void setUp() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		customValidator = new CustomValidator(validator);
	}

	@Test
	@DisplayName("양수필드에 음수가 들어가면 예외가 발생한다")
	void whenValidatePositiveFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.positiveNumber(-1) // 테스트 케이스에 맞게 변경
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("음수필드에 양수가 들어가면 예외가 발생한다")
	void whenValidateNegativeFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.negativeNumber(100) // 테스트 케이스에 맞게 변경
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("음수 혹은 0 허용인 필드에 양수가 들어가면 예외가 발생한다")
	void whenValidateNegativeOrZeroFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.negativeOrZero(100) // 테스트 케이스에 맞게 변경
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("크거나 같아야 하는 수에 작은 수가 들어가면 예외가 발생한다")
	void whenValidateMinFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.atLeast100(50) // 테스트 케이스에 맞게 변경
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("작거나 같아야 하는 수에 큰 수가 들어가면 예외가 발생한다")
	void whenValidateMaxFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.atMost100(300)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("크거나 같아야 하는 수에 작은 수가 들어가면 예외가 발생한다_decimal")
	void whenValidateDecimalMinFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.decimalAtLeast100(50)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("작거나 같아야 하는 수에 큰 수가 들어가면 예외가 발생한다_decimal")
	void whenValidateDecimalMaxFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.decimalAtMost100(300)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("정해진 자릿수 이상이 들어가면 예외가 발생한다")
	void whenValidateDigitsFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.numberWithAtMostFiveDigits(100_000)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("notBlank인 필드에 null이나 공백이 들어가면 예외가 발생한다")
	void whenValidateNotBlankFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture1 = createFixture()
			.toBuilder()
			.notBlankField("")
			.build();
		ValidationTestFixture newFixture2 = createFixture()
			.toBuilder()
			.notBlankField(null)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture1))
			.isInstanceOf(IllegalStateException.class);
		assertThatThrownBy(() -> customValidator.validateObject(newFixture2))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("notNull인 필드에 null이 들어가면 예외가 발생한다")
	void whenValidateNotNullFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.notNullField(null)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("email 형식이 알맞지 않으면 예외가 발생한다")
	void whenValidateEmailFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.emailField("test@")
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("과거나 현재날짜만 허용되는 필드가 미래일자면 예외가 발생한다")
	void whenValidatePastOrPresentFieldThenExceptionOccurs() {
		//given
		LocalDate tomorrow = LocalDate.now().plusDays(10);
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.pastOrPresentDate(tomorrow)
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	@Test
	@DisplayName("null이어야하는 필드가 null이 아니면 예외를 발생한다")
	void whenValidateNullFieldThenExceptionOccurs() {
		//given
		ValidationTestFixture newFixture = createFixture()
			.toBuilder()
			.nullField("not null!!!")
			.build();

		//when, then
		assertThatThrownBy(() -> customValidator.validateObject(newFixture))
			.isInstanceOf(IllegalStateException.class);
	}

	private ValidationTestFixture createFixture() {
		return ValidationTestFixture.builder()
			.positiveNumber(100)
			.negativeNumber(-1)
			.decimalAtLeast100(100)
			.decimalAtMost100(100)
			.numberWithAtMostFiveDigits(10000)
			.emailField("test@dduikka.com")
			.atLeast100(100)
			.atMost100(100)
			.negativeOrZero(0)
			.notBlankField("String")
			.notNullField("String")
			.nullField(null)
			.pastOrPresentDate(LocalDate.now())
			.build();
	}
}
