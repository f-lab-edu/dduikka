package com.flab.dduikka.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {

	public static String toLocalDateString(LocalDateTime localDateTime) {
		return localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	}

	public static String toLocalTimeString(LocalDateTime localDateTime) {
		return localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HHmm"));
	}

	/**
	 * param : 2024-04-25T18:00:00+09:00
	 */
	public static LocalDateTime toLocalDateTime(String localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		return LocalDateTime.parse(localDateTime, formatter);
	}

	public static LocalDateTime toLocalDateTime(String localDate, String localTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		return LocalDateTime.parse(String.format("%s%s", localDate, localTime), formatter);
	}
}
