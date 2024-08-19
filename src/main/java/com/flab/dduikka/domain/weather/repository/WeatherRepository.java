package com.flab.dduikka.domain.weather.repository;

import java.util.Optional;

import com.flab.dduikka.domain.weather.domain.Weather;

public interface WeatherRepository {

	Optional<Weather> findWeatherById(final String weatherId);

	void addWeather(Weather weather);
}
