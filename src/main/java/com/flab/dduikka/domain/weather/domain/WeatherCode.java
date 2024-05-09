package com.flab.dduikka.domain.weather.domain;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WeatherCode {
	T1H("기온"),
	RN1("1시간 강수량"),
	UUU("동서바람성분"),
	VVV("남북바람성분"),
	REH("습도"),
	PTY("강수형태"),
	LGT("낙뢰"),
	VEC("풍향"),
	WSD("풍속");

	private final String desc;

	public static WeatherCode stringToWeatherCode(String codeName) {
		return Arrays.stream(WeatherCode.values())
			.filter(value -> value.name().equals(codeName))
			.findFirst().orElseThrow();
	}
}
