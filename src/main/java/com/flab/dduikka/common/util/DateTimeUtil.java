package com.flab.dduikka.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateTimeUtil {

	public LocalDateTime setMinutesToZero(LocalDateTime localDateTime) {
		return localDateTime.withMinute(0).withSecond(0).withNano(0);
	}

	public String toLocalDateString(LocalDateTime localDateTime) {
		return localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	public String toLocalTimeString(LocalDateTime localDateTime) {
		return localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm"));
	}

	/**
	 * param : 2024-04-25T18:00:00+09:00
	 */
	public LocalDateTime toLocalDateTime(String localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		return LocalDateTime.parse(localDateTime, formatter);
	}

	public LocalDateTime toLocalDateTime(String localDate, String localTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		return LocalDateTime.parse(String.format("%s%s", localDate, localTime), formatter);
	}
}
