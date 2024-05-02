package com.flab.dduikka.domain.weather.facade;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.helper.JSONFileReader;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.application.KMAWeatherClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.KMAWeatherClientResponse;

import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class KMAWeatherFacadeTest {

	private KMAWeatherFacade kmaWeatherFacade;
	@Mock
	private KMAWeatherClient kmaWeatherClient;

	@BeforeEach
	void setUp() {
		kmaWeatherFacade = new KMAWeatherFacade(
			kmaWeatherClient,
			"testKey",
			1,
			8,
			"JSON"
		);
	}

	@Test
	@DisplayName("날씨 조회를 요청하면 webClient가 한 번 호출된다")
	void whenGetWeather_thenWebClientIsCalledOnce() throws IOException {
		//given
		KMAWeatherClientResponse mockResponse = JSONFileReader.readJSONFile(
			"/payload/weather/kma-weather-response.json",
			KMAWeatherClientResponse.class);
		given(kmaWeatherClient.getWeather(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(),
			anyString(), anyString()))
			.willReturn(mockResponse);
		LocalDateTime dateTime = LocalDateTime.now();

		//when
		String latitude = "55";
		String longitude = "127";
		String cityCode = "123456";
		kmaWeatherFacade.getWeather(dateTime, latitude, longitude, cityCode);

		//then
		verify(kmaWeatherClient, times(1))
			.getWeather(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(),
				anyString(), anyString());
	}

	@Test
	@DisplayName("날씨 조회를 요청하면 weather가 반환된다")
	void whenGetWeather_thenReturnsWeather() throws IOException {
		//given
		KMAWeatherClientResponse mockResponse = JSONFileReader.readJSONFile(
			"/payload/weather/kma-weather-response.json",
			KMAWeatherClientResponse.class);
		given(kmaWeatherClient.getWeather(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(),
			anyString(), anyString()))
			.willReturn(mockResponse);

		Location location = new Location("55", "127");
		LocalDateTime localDateTime = LocalDateTime.now();
		Weather weather =
			Weather.builder()
				.forecastDateTime(DateTimeUtil.toLocalDateTime("20240423", "1700"))
				.temperature(15.3)
				.relativeHumidity(55)
				.rainfall(0.0)
				.snowfall(0.0)
				.location(location)
				.requestDateTime(localDateTime)
				.build();

		//when
		String latitude = "55";
		String longitude = "127";
		String cityCode = "123456";
		Weather response = kmaWeatherFacade.getWeather(localDateTime, latitude, longitude, cityCode);

		//then
		assertThat(weather).isEqualTo(response);
	}

	@Test
	@DisplayName("날씨를 요청할 때 latitude나 longitude가 blank이면 ValidationException이 발생한다")
	void whenLatitudeOrLongitudeIsBlank_thenThrowValidationException() {
		//given
		LocalDateTime localDateTime = LocalDateTime.now();
		String latitude = "55";
		String longitude = "27";
		String cityCode = "123467";
		//when, then
		assertThatThrownBy(() -> kmaWeatherFacade.getWeather(localDateTime, "", longitude, cityCode))
			.isInstanceOf(ValidationException.class)
			.hasMessageContaining("blank일 수 없습니다.");
		assertThatThrownBy(() -> kmaWeatherFacade.getWeather(localDateTime, latitude, "", cityCode))
			.isInstanceOf(ValidationException.class)
			.hasMessageContaining("blank일 수 없습니다.");
		assertThatThrownBy(() -> kmaWeatherFacade.getWeather(localDateTime, "", "", cityCode))
			.isInstanceOf(ValidationException.class)
			.hasMessageContaining("blank일 수 없습니다.");
	}

	@Test
	@DisplayName("날씨를 요청할 때 citycode가 blank여도 정상으로 weather가 반환된다")
	void whenCodeCityIsBlank_thenReturnsWeather() throws IOException {
		//given
		KMAWeatherClientResponse mockResponse = JSONFileReader.readJSONFile(
			"/payload/weather/kma-weather-response.json",
			KMAWeatherClientResponse.class);
		given(kmaWeatherClient.getWeather(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(),
			anyString(), anyString()))
			.willReturn(mockResponse);

		Location location = new Location("55", "127");
		LocalDateTime localDateTime = LocalDateTime.now();
		Weather weather =
			Weather.builder()
				.forecastDateTime(DateTimeUtil.toLocalDateTime("20240423", "1700"))
				.temperature(15.3)
				.relativeHumidity(55)
				.rainfall(0.0)
				.snowfall(0.0)
				.location(location)
				.requestDateTime(localDateTime)
				.build();

		//when
		String latitude = "55";
		String longitude = "127";
		String cityCode = "";
		Weather response = kmaWeatherFacade.getWeather(localDateTime, latitude, longitude, cityCode);

		//then
		assertThat(weather).isEqualTo(response);
	}
}
