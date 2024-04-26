package com.flab.dduikka.common.util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeUtilTest {

	@Test
	@DisplayName("LocalDateTime을 LocalDate String 타입으로 변환한다")
	void whenToLocalDateString_thenReturnLocalDateString() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 4, 20, 10, 30);
		String localDateString = DateTimeUtil.toLocalDateString(localDateTime);
		assertThat(localDateString).isEqualTo("20240420");
	}

	@Test
	@DisplayName("LocalDateTime을 LocalDate String 타입으로 변환한다_fail")
	void whenToLocalDateString_thenReturnLocalDateString_fail() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 5, 20, 10, 30);
		String localDateString = DateTimeUtil.toLocalDateString(localDateTime);
		assertThat(localDateString).isNotEqualTo("20240420");
	}

	@Test
	@DisplayName("LocalDateTime을 LocalTime String 타입으로 변환한다")
	void whenToLocalTimeString_thenReturnLocalTimeString() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 4, 20, 05, 30);
		String localTimeString = DateTimeUtil.toLocalTimeString(localDateTime);
		assertThat(localTimeString).isEqualTo("0530");
	}

	@Test
	@DisplayName("LocalDateTime을 LocalTime String 타입으로 변환한다_fail")
	void whenToLocalTimeString_thenReturnLocalTimeString_fail() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 4, 20, 15, 30);
		String localTimeString = DateTimeUtil.toLocalTimeString(localDateTime);
		assertThat(localTimeString).isNotEqualTo("0530");
	}

	@Test
	@DisplayName("localdateTime String을 LocalDateTime 타입으로 변환한다")
	void whenToLocalDateTime_thenReturnsLocalDateTime() {
		String localDateTime = "2024-04-25T18:00:00+09:00";
		LocalDateTime localDate = DateTimeUtil.toLocalDateTime(localDateTime);
		assertThat(localDate)
			.isEqualTo(LocalDateTime.of(2024, 04, 25, 18, 00, 00));
	}

	@Test
	@DisplayName("localdateTime String을 LocalDateTime 타입으로 변환한다_fail")
	void whenToLocalDateTime_thenReturnsLocalDateTime_fail() {
		String localDateTime = "2024-05-25T18:00:00+09:00";
		LocalDateTime localDate = DateTimeUtil.toLocalDateTime(localDateTime);
		assertThat(localDate)
			.isNotEqualTo(LocalDateTime.of(2024, 04, 25, 18, 00, 00));
	}

	@Test
	@DisplayName("localDate와 localTime String을 LocalDateTime 타입으로 변환한다")
	void whenToLocalDateTimeGivenTwoString_thenReturnsLocalDateTime() {
		String localDate = "20240425";
		String localTime = "0710";
		LocalDateTime localDateTime = DateTimeUtil.toLocalDateTime(localDate, localTime);
		assertThat(localDateTime)
			.isEqualTo(LocalDateTime.of(2024, 04, 25, 07, 10, 00));
	}

	@Test
	@DisplayName("localDate와 localTime String을 LocalDateTime 타입으로 변환한다_fail")
	void whenToLocalDateTimeGivenTwoString_thenReturnsLocalDateTime_fail() {
		String localDate = "20240425";
		String localTime = "0710";
		LocalDateTime localDateTime = DateTimeUtil.toLocalDateTime(localDate, localTime);
		assertThat(localDateTime)
			.isNotEqualTo(LocalDateTime.of(2024, 05, 25, 07, 10, 00));
	}

}
