package com.flab.dduikka.domain.weather.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.IntegrationTestHelper;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;

class JdbcWeatherRepositoryTest extends IntegrationTestHelper {

	@Autowired
	private WeatherRepository weatherRepository;

	@Test
	@DisplayName("날씨를 등록한다.")
	void addWeather() {
		// given
		LocalDateTime forecastDate = LocalDateTime.of(2024, 07, 25, 21, 12);
		Weather mockWeather =
			Weather.builder()
				.weatherId("abcdedf")
				.forecastDateTime(forecastDate)
				.location(new Location("37", "125"))
				.temperature(33.0)
				.relativeHumidity(70)
				.rainfall(0.0)
				.snowfall(0.0)
				.createdAt(forecastDate)
				.build();

		// when
		weatherRepository.addWeather(mockWeather);

		// then
		Weather foundWeather =
			weatherRepository.findWeatherByIdAndForecastDatetime(mockWeather.getWeatherId(), forecastDate).get();
		assertThat(mockWeather).isEqualTo(foundWeather);
	}

	@Test
	@DisplayName("날씨를 조회한다.")
	void findWeather() {
		// given
		LocalDateTime forecastDate = LocalDateTime.of(2024, 07, 25, 21, 12);
		Weather mockWeather =
			Weather.builder()
				.weatherId("abcdedf")
				.forecastDateTime(forecastDate)
				.location(new Location("37", "125"))
				.temperature(33.0)
				.relativeHumidity(70)
				.rainfall(0.0)
				.snowfall(0.0)
				.createdAt(forecastDate)
				.build();
		weatherRepository.addWeather(mockWeather);

		// when
		Weather foundWeather =
			weatherRepository.findWeatherByIdAndForecastDatetime(mockWeather.getWeatherId(), forecastDate).get();

		// then
		assertThat(foundWeather).isEqualTo(mockWeather);
	}

}
