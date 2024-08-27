package com.flab.dduikka.domain.weather.application;

import static com.flab.dduikka.common.util.DateTimeUtil.DateTimePropery.*;
import static com.flab.dduikka.common.util.DateTimeUtil.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.flab.dduikka.domain.helper.WireMockServerHelper;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherAddRequestDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;

class WeatherServiceMockServerTest extends WireMockServerHelper {

	@Autowired
	private WeatherClientService weatherClientService;

	@BeforeEach
	void setUp() {
		wireMockServer.stop();
		wireMockServer.start();
	}

	@Test
	@DisplayName("날씨 요청 시 KMAWeatherClient가 호출되어 응답한다")
	void whenGetWeather_thenKMAWeatherClientIsCalled() throws IOException {
		//given
		String serviceKey = "KMAWeatherKey";
		int pageNo = 1;
		int numOfRows = 8;
		String dataType = "JSON";
		LocalDateTime requestDateTime = LocalDateTime.of(2024, 8, 22, 19, 40, 30);
		LocalDateTime modifiedDateTime = advanceTimeAndSetMintuesToZero(requestDateTime, ADVANCED_TIME.getValue());
		String nx = "55";
		String ny = "127";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("serviceKey", serviceKey);
		params.add("pageNo", String.valueOf(pageNo));
		params.add("numOfRows", String.valueOf(numOfRows));
		params.add("dataType", dataType);
		params.add("base_date", toLocalDateString(modifiedDateTime));
		params.add("base_time", toLocalTimeString(modifiedDateTime));
		params.add("nx", nx);
		params.add("ny", ny);
		String url = createUriString(KMA_PATH, params);
		Mocks.setupMockWeatherResponse(
			wireMockServer,
			url,
			readJsonFile("payload/weather/kma-weather-response.json"),
			HttpStatus.OK.value()
		);

		WeatherAddRequestDTO request = new WeatherAddRequestDTO(requestDateTime, nx, ny, null);

		//when
		Weather response = weatherClientService.getWeather(request);

		//then
		assertSoftly(softly -> {
			softly.assertThat(response.getTemperature()).isEqualTo(18.7);
			softly.assertThat(response.getRelativeHumidity()).isEqualTo(55);
			softly.assertThat(response.getRainfall()).isEqualTo(0.0);
			softly.assertThat(response.getSnowfall()).isEqualTo(0.0);
		});
	}

	@Test
	@DisplayName("날씨 첫요청 시 Exception이 발생하면 AccuWeatherClient가 호출되어 응답한다")
	void whenGetWeather_thenAccuWeatherClientIsCalled() throws IOException {
		//given
		String cityCode = "test";
		String apikey = "accuWeatherKey";
		String language = "ko-kr";

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

		LocalDateTime now = LocalDateTime.now();
		String nx = "55";
		String ny = "127";

		//when
		WeatherAddRequestDTO request = new WeatherAddRequestDTO(now, nx, ny, cityCode);

		//when
		Weather response = weatherClientService.getWeather(request);

		//then
		assertSoftly(softly -> {
			softly.assertThat(response.getTemperature()).isEqualTo(15.3);
			softly.assertThat(response.getRelativeHumidity()).isEqualTo(61);
			softly.assertThat(response.getRainfall()).isEqualTo(0.0);
			softly.assertThat(response.getSnowfall()).isEqualTo(0.0);
		});
	}

	@Test
	@DisplayName("모든 요청이 오류가 나면 ExternalAPIException이 발생한다.")
	void whenGetWeather_thenThrowExternalAPIException() throws IOException {
		//given
		String cityCode = "test";
		String apikey = "accuWeatherKey";
		String language = "ko-kr";

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

		LocalDateTime now = LocalDateTime.now();
		String nx = "55";
		String ny = "127";
		WeatherAddRequestDTO request = new WeatherAddRequestDTO(now, nx, ny, cityCode);

		//when

		//when, then
		assertThatThrownBy(() -> weatherClientService.getWeather(request))
			.isInstanceOf(WeatherException.ExternalAPIException.class)
			.hasMessageContaining("외부 API에서 오류가 발생하였습니다.");
	}

}
