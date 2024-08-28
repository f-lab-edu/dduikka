package com.flab.dduikka.domain.weather.dto;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.common.util.GeoHashUtil;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;

class AccuWeatherFeignClientResponseTest {

	@Test
	@DisplayName("json 응답이 객체로 역직렬화된다")
	void whenCreateClass_thenSuccessfullyDeserialized() throws IOException {
		AccuWeatherClientResponseDTO response =
			readJSONFile("/payload/weather/accu-weather-response.json");

		assertThat(response.getDateTime()).isEqualTo("2024-04-26T22:00:00+09:00");
		assertThat(response.getRelativeHumidity()).isEqualTo(61);
		assertThat(response.getTemperature().getValue()).isEqualTo(15.3);
		assertThat(response.getRain().getValue()).isZero();
		assertThat(response.getSnow().getValue()).isZero();
	}

	@Test
	@DisplayName("응답 객체를 전달하면 weather 객체를 정상적으로 생성한다")
	void whenDtoFromWeather_thenWeatherIsCorrectlyCreated() throws IOException {
		//given
		Location location = new Location("55", "127");
		Weather weather =
			Weather.builder()
				.weatherId(GeoHashUtil.getGeoHashString(location.getLatitude(), location.getLongitude()))
				.forecastDateTime(DateTimeUtil.toLocalDateTime("2024-04-26T22:00:00+09:00"))
				.temperature(15.3)
				.relativeHumidity(61)
				.rainfall(0.0)
				.snowfall(0.0)
				.location(location)
				.build();
		AccuWeatherClientResponseDTO response =
			readJSONFile("/payload/weather/accu-weather-response.json");

		//when
		Weather newWeather =
			AccuWeatherClientResponseDTO.from(response, location);

		//then
		assertThat(weather).isEqualTo(newWeather);

	}

	private AccuWeatherClientResponseDTO readJSONFile(String path) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Class clazz = AccuWeatherFeignClientResponseTest.class;
		InputStream stream = clazz.getResourceAsStream(path);
		JsonNode jsonNode = mapper.readTree(stream);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.treeToValue(jsonNode, AccuWeatherClientResponseDTO[].class)[0];
	}

}
