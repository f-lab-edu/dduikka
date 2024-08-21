package com.flab.dduikka.domain.weather.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.flab.dduikka.domain.weather.domain.Weather;

public interface WeatherRepository {

	Optional<Weather> findWeatherByIdAndForecastDatetime(final String weatherId, final LocalDateTime forecastTime);

	void addWeather(Weather weather);
}
