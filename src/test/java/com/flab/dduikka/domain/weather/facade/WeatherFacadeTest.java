package com.flab.dduikka.domain.weather.facade;

import static org.assertj.core.api.BDDAssertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.flab.dduikka.common.util.GeoHashUtil;
import com.flab.dduikka.domain.helper.IntegrationTestHelper;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.application.WeatherClientService;
import com.flab.dduikka.domain.weather.application.WeatherService;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherAddRequestDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;
import com.flab.dduikka.domain.weather.repository.WeatherRepository;

class WeatherFacadeTest extends IntegrationTestHelper {

	@Autowired
	private WeatherFacade weatherFacade;

	@Autowired
	private WeatherService weatherService;

	@MockBean
	private WeatherClientService weatherClientService;

	@Autowired
	private WeatherRepository weatherRepository;

	@Test
	@DisplayName("날씨가 있으면 프로세스가 정상 종료된다.")
	void whenFindWeather_ThenCompleteProcess() {
		// given
		String latitude = "55";
		String longitude = "127";
		LocalDateTime now = LocalDateTime.now();
		Location location = new Location(latitude, longitude);

		WeatherAddRequestDTO request
			= new WeatherAddRequestDTO(now, latitude, longitude, null);

		Weather newWeather = Weather.builder()
			.weatherId(GeoHashUtil.getGeoHashString(latitude, longitude))
			.forecastDateTime(request.getForecastDatetime())
			.temperature(32.0)
			.relativeHumidity(70)
			.rainfall(0.0)
			.snowfall(0.0)
			.location(location)
			.createdAt(now)
			.build();

		weatherService.addWeather(newWeather);

		// when
		weatherFacade.addWeather(request);

		// then
		BDDMockito.then(weatherClientService).should(never()).getWeather(any());
	}

	@Test
	@DisplayName("날씨가 DB에 존재하지 않아 날씨 API 호출 시 에러가 발생하면 예외가 발생한다.")
	void whenNotFoundWeatherAndApiCalled_thenThrowsException() {
		// given
		String latitude = "55";
		String longitude = "127";
		LocalDateTime now = LocalDateTime.now();

		WeatherAddRequestDTO request
			= new WeatherAddRequestDTO(now, latitude, longitude, null);

		given(weatherClientService.getWeather(any())).willThrow(WeatherException.ExternalAPIException.class);

		// when, then
		thenThrownBy(() -> weatherFacade.addWeather(request))
			.isInstanceOf(WeatherException.ExternalAPIException.class);
	}

	@Test
	@DisplayName("날씨가 DB에 존재하지 않아 날씨를 저장한다.")
	void whenNotFoundWeather_thenAddWeather() {
		// given
		String latitude = "55";
		String longitude = "127";
		LocalDateTime now = LocalDateTime.now();
		Location location = new Location(latitude, longitude);

		WeatherAddRequestDTO request
			= new WeatherAddRequestDTO(now, latitude, longitude, null);

		Weather mockWeather = Weather.builder()
			.weatherId(GeoHashUtil.getGeoHashString(latitude, longitude))
			.forecastDateTime(request.getForecastDatetime())
			.temperature(32.0)
			.relativeHumidity(70)
			.rainfall(0.0)
			.snowfall(0.0)
			.location(location)
			.createdAt(now)
			.build();

		given(weatherClientService.getWeather(any())).willReturn(mockWeather);

		// when
		weatherFacade.addWeather(request);

		// then
		Weather foundWeather = weatherRepository.findWeatherByIdAndForecastDatetime(
			mockWeather.getWeatherId(), mockWeather.getForecastDateTime()).get();

		assertSoftly(softly -> {
			softly.assertThat(foundWeather.getWeatherId()).isEqualTo(mockWeather.getWeatherId());
			softly.assertThat(foundWeather.getForecastDateTime()).isEqualTo(mockWeather.getForecastDateTime());
			softly.assertThat(foundWeather.getRainfall()).isEqualTo(mockWeather.getRainfall());
			softly.assertThat(foundWeather.getSnowfall()).isEqualTo(mockWeather.getSnowfall());
			softly.assertThat(foundWeather.getRelativeHumidity()).isEqualTo(mockWeather.getRelativeHumidity());
			softly.assertThat(foundWeather.getTemperature()).isEqualTo(mockWeather.getTemperature());
		});
	}
}
