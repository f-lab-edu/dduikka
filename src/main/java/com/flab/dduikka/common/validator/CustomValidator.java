package com.flab.dduikka.common.validator;

import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomValidator {

	private final Validator validator;

	public void validateObject(Object object) {
		Set<ConstraintViolation<Object>> violations = validator.validate(object);
		ConstraintViolation<Object> violation = violations.stream()
			.findFirst()
			.orElse(null);

		if (violation != null) {
			String errorMessage =
				String.format("%s 필드는 %s.", violation.getPropertyPath(), violation.getMessage());
			throw new IllegalStateException(errorMessage);
		}
	}
}
