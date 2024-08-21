package com.flab.dduikka.domain.weather.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.client.WeatherClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;
import com.flab.dduikka.domain.weather.repository.WeatherRepository;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

	private WeatherService weatherService;
	private WeatherRepository weatherRepository;
	private List<WeatherClient> weatherClientList;

	@BeforeEach
	void setUp() {
		// Integer, String이 mocking 불가하기 때문에 setup을 활용하여 빈 생성 및 주입
		WeatherClient weatherClient = Mockito.mock(WeatherClient.class);
		weatherClientList = Collections.singletonList(weatherClient);
		weatherRepository = Mockito.mock(WeatherRepository.class);
		weatherService = new WeatherService(10, weatherClientList, weatherRepository);
	}

	@Test
	@DisplayName("날씨를 조회한다")
	void whenFindWeather_thenReturnWeather() {
		// given
		LocalDateTime now = LocalDateTime.now();
		WeatherRequestDTO request =
			new WeatherRequestDTO(now, "55", "127");
		Weather mockWeather =
			Weather.builder()
				.weatherId("abcdedf")
				.forecastDateTime(now)
				.location(new Location(request.getLatitude(), request.getLongitude()))
				.temperature(33.0)
				.relativeHumidity(70)
				.rainfall(0.0)
				.snowfall(0.0)
				.createdAt(now)
				.build();
		given(weatherRepository.findWeatherByIdAndForecastDatetime(anyString(), any())).willReturn(
			Optional.of(mockWeather));

		// when
		WeatherResponseDTO response = weatherService.findWeather(request);

		// then
		assertSoftly(softly -> {
			softly.assertThat(response.getTemperature()).isEqualTo(mockWeather.getTemperature());
			softly.assertThat(response.getRainfall()).isEqualTo(mockWeather.getRainfall());
			softly.assertThat(response.getSnowfall()).isEqualTo(mockWeather.getSnowfall());
			softly.assertThat(response.getRelativeHumidity()).isEqualTo(mockWeather.getRelativeHumidity());
		});

	}

	@Test
	@DisplayName("저장되지 않은 날씨를 조회하면 예외가 발생한다.")
	void whenFindWeather_thenThrowException() {
		// given
		LocalDateTime now = LocalDateTime.now();
		given(weatherRepository.findWeatherByIdAndForecastDatetime(anyString(), any())).willReturn(Optional.empty());
		WeatherRequestDTO request =
			new WeatherRequestDTO(now, "55", "127");

		// when, then
		assertThatThrownBy(() -> weatherService.findWeather(request))
			.isInstanceOf(WeatherException.WeatherNotFoundException.class)
			.hasMessageContaining("해당 위치에 대한 날씨가 존재하지 않습니다");
	}

}
