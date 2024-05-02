package com.flab.dduikka.domain.weather.dto;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.helper.JSONFileReader;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;

class KMAWeatherClientResponseTest {

	@Test
	@DisplayName("json 응답이 객체로 역직렬화된다")
	void whenCreateClass_thenSuccessfullyDeserialized() throws IOException {
		//given, when
		KMAWeatherClientResponse response =
			JSONFileReader.readJSONFile("/payload/weather/kma-weather-response.json", KMAWeatherClientResponse.class);

		KMAWeatherClientResponse.WeatherPayload payload = response.getResponse();

		//then
		assertThat(payload.getHeader().getResultCode()).isEqualTo("00");
		assertThat(payload.getHeader().getResultMsg()).isEqualTo("NORMAL_SERVICE");
		assertThat(payload.getBody().getItems().getItem().get(0).getBaseDate()).isEqualTo("20240423");
		assertThat(payload.getBody().getItems().getItem().get(0).getBaseTime()).isEqualTo("1700");
		assertThat(payload.getBody().getItems().getItem().get(0).getNx()).isEqualTo(55.0);
		assertThat(payload.getBody().getItems().getItem().get(0).getNy()).isEqualTo(127.0);
	}

	@Test
	@DisplayName("응답 객체를 전달하면 weather 객체를 정상적으로 생성한다")
	void whenDtoFromWeather_thenWeatherIsCorrectlyCreated() throws IOException {
		//given
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
		KMAWeatherClientResponse response =
			JSONFileReader.readJSONFile("/payload/weather/kma-weather-response.json", KMAWeatherClientResponse.class);

		//when
		Weather newWeather =
			KMAWeatherClientResponse.from(response, location, localDateTime);

		//then
		assertThat(weather).isEqualTo(newWeather);

	}
}
