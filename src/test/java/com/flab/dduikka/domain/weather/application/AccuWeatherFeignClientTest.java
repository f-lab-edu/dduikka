package com.flab.dduikka.domain.weather.application;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.flab.dduikka.domain.helper.WireMockServerHelper;
import com.flab.dduikka.domain.weather.dto.AccuWeatherClientResponseDTO;

import feign.FeignException;

class AccuWeatherFeignClientTest extends WireMockServerHelper {

	@Autowired
	private AccuWeatherFeignClient accuWeatherFeignClient;

	@BeforeEach
	void setUp() {
		wireMockServer.stop();
		wireMockServer.start();
	}

	@Test
	@DisplayName("정상적으로 날씨를 호출하면 응답된다")
	void whenGetWeather200_ThenSuccessfullyResponse() throws IOException {
		//given
		String cityCode = "test";
		String apikey = "JSON";
		String language = "ko-kr";
		boolean details = true;
		boolean metric = true;

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("apikey", apikey);
		params.add("language", language);
		params.add("details", "true");
		params.add("metric", "true");
		String url = createUriString(String.format("%s/%s", ACCU_WEATHER_PATH, cityCode), params);
		Mocks.setupMockWeatherResponse(
			wireMockServer,
			url,
			readJsonFile("payload/weather/accu-weather-response.json"),
			HttpStatus.OK.value()
		);

		//when
		AccuWeatherClientResponseDTO response =
			accuWeatherFeignClient.getWeather(
				cityCode,
				apikey,
				language,
				details,
				metric
			).get(0);

		//then
		assertThat(response.getDateTime()).isEqualTo("2024-04-26T22:00:00+09:00");
		assertThat(response.getRelativeHumidity()).isEqualTo(61);
		assertThat(response.getTemperature().getValue()).isEqualTo(15.3);
	}

	@Test
	@DisplayName("정상 요청이 아니면 FeignException 오류가 발생한다")
	void whenGetWeather_ThenThrowsFeignException() throws IOException {
		//given
		//given
		String cityCode = "test";
		String apikey = "JSON";
		String language = "ko-kr";
		boolean details = true;
		boolean metric = true;

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("apikey", apikey);
		params.add("language", language);
		params.add("details", "true");
		params.add("metric", "true");
		String url = createUriString(String.format("%s/%s", ACCU_WEATHER_PATH, cityCode), params);
		Mocks.setupMockWeatherResponse(
			wireMockServer,
			url,
			null,
			HttpStatus.BAD_REQUEST.value()
		);

		//when, then
		assertThatThrownBy(() -> accuWeatherFeignClient.getWeather(
			cityCode,
			apikey,
			language,
			details,
			metric
		)).isInstanceOf(FeignException.class);
	}
}

