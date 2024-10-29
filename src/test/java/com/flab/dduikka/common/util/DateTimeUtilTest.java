package com.flab.dduikka.common.util;

import static com.flab.dduikka.common.util.DateTimeUtil.*;
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
		String localDateString = toLocalDateString(localDateTime);
		assertThat(localDateString).isEqualTo("20240420");
	}

	@Test
	@DisplayName("LocalDateTime을 LocalDate String 타입으로 변환한다_fail")
	void whenToLocalDateString_thenReturnLocalDateString_fail() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 5, 20, 10, 30);
		String localDateString = toLocalDateString(localDateTime);
		assertThat(localDateString).isNotEqualTo("20240420");
	}

	@Test
	@DisplayName("LocalDateTime을 LocalTime String 타입으로 변환한다")
	void whenToLocalTimeString_thenReturnLocalTimeString() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 4, 20, 05, 30);
		String localTimeString = toLocalTimeString(localDateTime);
		assertThat(localTimeString).isEqualTo("0530");
	}

	@Test
	@DisplayName("LocalDateTime을 LocalTime String 타입으로 변환한다_fail")
	void whenToLocalTimeString_thenReturnLocalTimeString_fail() {
		LocalDateTime localDateTime =
			LocalDateTime.of(2024, 4, 20, 15, 30);
		String localTimeString = toLocalTimeString(localDateTime);
		assertThat(localTimeString).isNotEqualTo("0530");
	}

	@Test
	@DisplayName("localdateTime String을 LocalDateTime 타입으로 변환한다")
	void whenToLocalDateTime_thenReturnsLocalDateTime() {
		String localDateTime = "2024-04-25T18:00:00+09:00";
		LocalDateTime localDate = toLocalDateTime(localDateTime);
		assertThat(localDate)
			.isEqualTo(LocalDateTime.of(2024, 04, 25, 18, 00, 00));
	}

	@Test
	@DisplayName("localdateTime String을 LocalDateTime 타입으로 변환한다_fail")
	void whenToLocalDateTime_thenReturnsLocalDateTime_fail() {
		String localDateTime = "2024-05-25T18:00:00+09:00";
		LocalDateTime localDate = toLocalDateTime(localDateTime);
		assertThat(localDate)
			.isNotEqualTo(LocalDateTime.of(2024, 04, 25, 18, 00, 00));
	}

	@Test
	@DisplayName("localDate와 localTime String을 LocalDateTime 타입으로 변환한다")
	void whenToLocalDateTimeGivenTwoString_thenReturnsLocalDateTime() {
		String localDate = "20240425";
		String localTime = "0710";
		LocalDateTime localDateTime = toLocalDateTime(localDate, localTime);
		assertThat(localDateTime)
			.isEqualTo(LocalDateTime.of(2024, 04, 25, 07, 10, 00));
	}

	@Test
	@DisplayName("localDate와 localTime String을 LocalDateTime 타입으로 변환한다_fail")
	void whenToLocalDateTimeGivenTwoString_thenReturnsLocalDateTime_fail() {
		String localDate = "20240425";
		String localTime = "0710";
		LocalDateTime localDateTime = toLocalDateTime(localDate, localTime);
		assertThat(localDateTime)
			.isNotEqualTo(LocalDateTime.of(2024, 05, 25, 07, 10, 00));
	}

	@Test
	@DisplayName("분초를 초기화한다_success")
	void setMinutesToZero_success() {
		// given
		int year = 2024;
		int month = 10;
		int dayOfMonth = 29;
		int hour = 16;
		int minute = 43;
		LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, 20, 23);

		// when
		LocalDateTime changedLocalDateTime = setMinutesToZero(localDateTime);

		// then
		assertThat(changedLocalDateTime).isEqualTo(LocalDateTime.of(year, month, dayOfMonth, hour, 0, 0));
	}

	@Test
	@DisplayName("minusMinutes만큼 현재 시간에서 빼고 분초를 초기화한다_성공")
	void advanceTimeAndSetMintuesToZero_success() {
		// given
		int year = 2024;
		int month = 10;
		int dayOfMonth = 29;
		int hour = 16;
		int minute = 43;
		LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, 20, 23);

		// when
		LocalDateTime advancedTime = advanceTimeAndSetMintuesToZero(localDateTime, 10);

		// then
		assertThat(advancedTime).isEqualTo(LocalDateTime.of(year, month, dayOfMonth, hour, 0, 0));
	}

	@Test
	@DisplayName("현재 시간이 4시일 때 3시로 시간을 앞당긴다_성공")
	void advanceTimeAndSetMintuesToZero2_success() {
		// given
		int year = 2024;
		int month = 10;
		int dayOfMonth = 29;
		int hour = 16;
		int minute = 4;
		LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute, 20, 23);

		// when
		LocalDateTime advancedTime = advanceTimeAndSetMintuesToZero(localDateTime, 10);
		System.out.println(advancedTime);

		// then
		assertThat(advancedTime).isEqualTo(LocalDateTime.of(year, month, dayOfMonth, hour - 1, 0, 0));
	}

}
