package com.flab.dduikka.domain.weather.dto;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.helper.JSONFileReader;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;

class KMAWeatherFeignClientResponseTest {

	@Test
	@DisplayName("json 응답이 객체로 역직렬화된다")
	void whenCreateClass_thenSuccessfullyDeserialized() throws IOException {
		//given, when
		KMAWeatherClientResponseDTO response =
			JSONFileReader.readJSONFile("/payload/weather/kma-weather-response.json",
				KMAWeatherClientResponseDTO.class);

		KMAWeatherClientResponseDTO.WeatherPayload payload = response.getResponse();

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
		Weather weather =
			Weather.builder()
				.forecastDateTime(DateTimeUtil.toLocalDateTime("20240423", "1700"))
				.temperature(15.3)
				.relativeHumidity(55)
				.rainfall(0.0)
				.snowfall(0.0)
				.location(location)
				.build();
		KMAWeatherClientResponseDTO response =
			JSONFileReader.readJSONFile("/payload/weather/kma-weather-response.json",
				KMAWeatherClientResponseDTO.class);

		//when
		Weather newWeather =
			KMAWeatherClientResponseDTO.from(response, location);

		//then
		assertThat(weather).isEqualTo(newWeather);

	}
}
