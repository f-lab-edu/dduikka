package com.flab.dduikka.common.util;

import java.time.LocalDate;

public class CustomValidator {

	private CustomValidator() {
	}

	//TODO : 커스텀 예외 추가 시 예외 변경하기
	public static void notBlank(String value, String fieldValue) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("%s는 null 또는 공백일 수 없습니다.".formatted(fieldValue));
		}
	}

	//TODO : 커스텀 예외 추가 시 예외 변경하기
	public static void notNull(Object value, String fieldValue) {
		if (value == null) {
			throw new IllegalArgumentException("%s는 null일 수 없습니다.".formatted(fieldValue));
		}
	}

	//TODO : 커스텀 예외 추가 시 예외 변경하기
	public static void pastOrPresent(LocalDate value, String fieldValue) {
		LocalDate now = LocalDate.now();
		if (value.isAfter(now)) {
			throw new IllegalArgumentException("%s는 미래날짜일 수 없습니다.".formatted(fieldValue));
		}
	}

}
