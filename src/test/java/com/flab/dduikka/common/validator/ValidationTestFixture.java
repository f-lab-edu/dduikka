package com.flab.dduikka.common.validator;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public class ValidationTestFixture {

	@Positive(message = "양수여야만 합니다")
	private int positiveNumber;
	@Negative(message = "음수여야만 합니다")
	private int negativeNumber;
	@NegativeOrZero(message = "음수이거나 0이어야만 합니다")
	private int negativeOrZero;
	@Min(message = "크거나 같아야 합니다", value = 100)
	private int atLeast100;
	@Max(message = "작거나 같아야 합니다", value = 100)
	private int atMost100;
	@DecimalMin(message = "크거나 같아야 합니다", value = "100")
	private int decimalAtLeast100;
	@DecimalMax(message = "작거나 같아야 합니다", value = "100")
	private int decimalAtMost100;
	@Digits(message = "6자리 이내여야만 합니다", integer = 5, fraction = 5)
	private int numberWithAtMostFiveDigits;
	@NotBlank(message = "null이거나 공백이면 안 됩니다")
	private String notBlankField;
	@NotEmpty(message = "null이면 안 됩니다")
	private String notNullField;
	@Email(message = "이메일 형식에 맞지 않습니다")
	private String emailField;
	@PastOrPresent(message = "날짜가 과거이거나 현재여야만 합니다")
	private LocalDate pastOrPresentDate;
	@Null(message = "null이어야만 합니다")
	private String nullField;

}
