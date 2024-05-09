package com.flab.dduikka.domain.weather.facade;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.application.AccuWeatherFeignClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.AccuWeatherClientResponse;

import jakarta.validation.ValidationException;

@Component
@Order(value = 2)
public class AccuWeatherClient implements WeatherClient {

	private final AccuWeatherFeignClient weatherFeignClient;
	private final String serviceKey;
	private final String language;
	private final boolean details;
	private final boolean metric;

	@Autowired
	public AccuWeatherClient(
		AccuWeatherFeignClient weatherFeignClient,
		@Value("#{environment['external.api-key.accu-weather']}") String serviceKey,
		@Value("#{environment['external.variable.accu-weather.language']}") String language,
		@Value("#{environment['external.variable.accu-weather.details']}") boolean details,
		@Value("#{environment['external.variable.accu-weather.metric']}") boolean metric
	) {
		this.weatherFeignClient = weatherFeignClient;
		this.serviceKey = serviceKey;
		this.language = language;
		this.details = details;
		this.metric = metric;
	}

	@Override
	public Weather getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode) {
		validated(latitude, longitude, cityCode);
		return AccuWeatherClientResponse.from(
			weatherFeignClient.getWeather(
				cityCode,
				serviceKey,
				language,
				details,
				metric
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
