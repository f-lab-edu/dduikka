package com.flab.dduikka.domain.weather.client;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.application.AccuWeatherFeignClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.AccuWeatherClientResponseDTO;
import com.flab.dduikka.domain.weather.property.AccuWeatherProperty;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Component
@Order(value = 2)
@RequiredArgsConstructor
public class AccuWeatherClient implements WeatherClient {

	private final AccuWeatherFeignClient weatherFeignClient;
	private final AccuWeatherProperty accuWeatherProperty;

	@Override
	public Weather getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode) {
		validated(latitude, longitude, cityCode);
		return AccuWeatherClientResponseDTO.from(
			weatherFeignClient.getWeather(
				cityCode,
				accuWeatherProperty.getApiKey(),
				accuWeatherProperty.getLanguage(),
				accuWeatherProperty.isDetails(),
				accuWeatherProperty.isMetric()
			).get(0),
			new Location(latitude, longitude),
			dateTime
		);
	}

	private void validated(String latitude, String longitude, String cityCode) {
		if (latitude.isBlank() || longitude.isBlank() || cityCode.isBlank())
			throw new ValidationException(String.format("blank일 수 없습니다. %s", cityCode));
	}
}
