package com.flab.dduikka.domain.weather.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flab.dduikka.domain.weather.exception.WeatherException;

class KMAWeatherResultCodeTest {

	@Test
	@DisplayName("결과 코드가 00인 경우 예외가 발생하지 않는다")
	void whenCheckErrorCode_00_thenNotThrowException() {
		//00일 때는 예외가 발생하지 않는다.
		assertThatNoException()
			.isThrownBy(() -> KMAWeatherResultCode.checkErrorCode("00"));
	}

	@Test
	@DisplayName("결과 코드가 00이 아닌 경우 ExternalAPIException 예외가 발생한다")
	void whenCheckErrorCode_not00_thenThrowExternalAPIException() {
		//00이 아닐 때는 예외가 발생한다.
		Arrays.stream(KMAWeatherResultCode.values())
			.filter(resultCode -> !resultCode.getCode().equals("00"))
			.forEach(resultCode ->
				assertThatThrownBy(() -> KMAWeatherResultCode.checkErrorCode(resultCode.getCode()))
					.isInstanceOf(WeatherException.ExternalAPIException.class)
					.hasMessageContaining(resultCode.getDesc())
			);
	}

}
