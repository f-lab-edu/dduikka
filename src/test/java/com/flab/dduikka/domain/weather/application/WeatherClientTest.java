package com.flab.dduikka.domain.weather.application;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.helper.WireMockServerHelper;
import com.flab.dduikka.domain.weather.dto.AccuWeatherClientResponse;
import com.flab.dduikka.domain.weather.dto.KMAWeatherClientResponse;

import feign.FeignException;

class WeatherClientTest extends WireMockServerHelper {

	@BeforeEach
	void setUp() {
		wireMockServer.stop();
		wireMockServer.start();
	}

	@Test
	@DisplayName("정상적으로 날씨를 호출하면 00 상태 코드가 전달된다_기상청")
	void whenGetKmaWeather200_ThenReturns00Status() throws IOException {
		//given
		String serviceKey = "test";
		int pageNo = 1;
		int numOfRows = 10;
		String dataType = "JSON";
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDateString = DateTimeUtil.toLocalDateString(localDateTime);
		String localTimeString = DateTimeUtil.toLocalTimeString(localDateTime);
		String nx = "55";
		String ny = "127";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("serviceKey", serviceKey);
		params.add("pageNo", String.valueOf(pageNo));
		params.add("numOfRows", String.valueOf(numOfRows));
		params.add("dataType", dataType);
		params.add("base_date", localDateString);
		params.add("base_time", localTimeString);
		params.add("nx", nx);
		params.add("ny", ny);
		String url = createUriString(KMA_PATH, params);
		Mocks.setupMockWeatherResponse(
			wireMockServer,
			url,
			readJsonFile("payload/weather/kma-weather-response.json"),
			HttpStatus.OK.value()
		);
		//when
		KMAWeatherClientResponse response =
			kmaWeatherFeignClient.getWeather(
				serviceKey,
				pageNo,
				numOfRows,
				dataType,
				localDateString,
				localTimeString,
				nx,
				ny);
		//then
		assertThat(response.getResponse().getHeader().getResultCode()).isEqualTo("00");
	}

	@Test
	@DisplayName("정상 요청이 아니면 FeignException 오류가 발생한다_기상청")
	void whenGetWeather400_ThenThrowsFeignException() throws IOException {
		//given
		String serviceKey = "test";
		int pageNo = 1;
		int numOfRows = 10;
		String dataType = "JSON";
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDateString = DateTimeUtil.toLocalDateString(localDateTime);
		String localTimeString = DateTimeUtil.toLocalTimeString(localDateTime);
		String nx = "57";
		String ny = "127";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("serviceKey", serviceKey);
		params.add("pageNo", String.valueOf(pageNo));
		params.add("numOfRows", String.valueOf(numOfRows));
		params.add("dataType", dataType);
		params.add("base_date", localDateString);
		params.add("base_time", localTimeString);
		params.add("nx", nx);
		params.add("ny", ny);
		String url = createUriString(KMA_PATH, params);
		Mocks.setupMockWeatherResponse(wireMockServer, url, null, HttpStatus.BAD_REQUEST.value());

		//when, then
		assertThatThrownBy(() ->
			kmaWeatherFeignClient.getWeather(
				serviceKey,
				pageNo,
				numOfRows,
				dataType,
				localDateString,
				localTimeString,
				nx,
				ny))
			.isInstanceOf(FeignException.class);
	}

	@Test
	@DisplayName("정상적으로 날씨를 호출하면 응답된다_아큐웨더")
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
		AccuWeatherClientResponse response =
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
}
