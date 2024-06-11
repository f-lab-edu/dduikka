package com.flab.dduikka.domain.weather.client;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.helper.JSONFileReader;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.application.AccuWeatherFeignClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.AccuWeatherClientResponse;
import com.flab.dduikka.domain.weather.property.AccuWeatherProperty;

import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
class AccuWeatherClientTest {
	private AccuWeatherClient accuWeatherFacade;
	@Mock
	private AccuWeatherFeignClient accuWeatherFeignClient;

	@BeforeEach
	void setUp() {
		accuWeatherFacade =
			new AccuWeatherClient
				(
					accuWeatherFeignClient,
					new AccuWeatherProperty("test", "ko-kr", true, true)
				);
	}

	@Test
	@DisplayName("날씨 조회를 요청하면 weather client가 한 번 호출된다")
	void whenGetWeather_thenWeatherClientIsCalledOnce() throws IOException {
		//given
		AccuWeatherClientResponse[] mockResponse =
			JSONFileReader.readJSONFile
				(
					"/payload/weather/accu-weather-response.json",
					AccuWeatherClientResponse[].class
				);
		LocalDateTime dateTime = LocalDateTime.now();
		given(accuWeatherFeignClient.getWeather(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean()))
			.willReturn(List.of(mockResponse));

		//when
		String latitude = "55";
		String longitude = "127";
		String cityCode = "123456";
		accuWeatherFacade.getWeather(dateTime, latitude, longitude, cityCode);

		//then
		verify(accuWeatherFeignClient, times(1))
			.getWeather(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean());
	}

	@Test
	@DisplayName("날씨 조회를 요청하면 weather가 반환된다")
	void whenGetWeather_thenReturnsWeather() throws IOException {
		//given
		AccuWeatherClientResponse[] mockResponse =
			JSONFileReader.readJSONFile
				(
					"/payload/weather/accu-weather-response.json",
					AccuWeatherClientResponse[].class
				);
		Location location = new Location("55", "127");
		LocalDateTime localDateTime = LocalDateTime.now();
		Weather weather =
			Weather.builder()
				.forecastDateTime(DateTimeUtil.toLocalDateTime("2024-04-26T22:00:00+09:00"))
				.temperature(15.3)
				.relativeHumidity(61)
				.rainfall(0.0)
				.snowfall(0.0)
				.location(location)
				.requestDateTime(localDateTime)
				.build();
		given(accuWeatherFeignClient.getWeather(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean()))
			.willReturn(List.of(mockResponse));

		//when
		String latitude = "55";
		String longitude = "127";
		String cityCode = "123456";
		Weather response =
			accuWeatherFacade.getWeather(localDateTime, latitude, longitude, cityCode);

		//then
		assertThat(response).isEqualTo(weather);
	}

	@Test
	@DisplayName("날씨를 요청할 때 citycode나 latitude나 longitude가 blank이면 ValidationException이 발생한다")
	void whenCityCodeOrLatitudeOrLongitudeIsBlank_thenThrowValidationException() {
		//given
		LocalDateTime localDateTime = LocalDateTime.now();
		String latitude = "23";
		String longitude = "127";
		String cityCode = "123456";
		//when, then
		assertThatThrownBy(() -> accuWeatherFacade.getWeather(localDateTime, "", longitude, cityCode))
			.isInstanceOf(ValidationException.class)
			.hasMessageContaining("blank일 수 없습니다.");
		assertThatThrownBy(() -> accuWeatherFacade.getWeather(localDateTime, latitude, "", cityCode))
			.isInstanceOf(ValidationException.class)
			.hasMessageContaining("blank일 수 없습니다.");
		assertThatThrownBy(() -> accuWeatherFacade.getWeather(localDateTime, latitude, longitude, ""))
			.isInstanceOf(ValidationException.class)
			.hasMessageContaining("blank일 수 없습니다.");
	}

}
