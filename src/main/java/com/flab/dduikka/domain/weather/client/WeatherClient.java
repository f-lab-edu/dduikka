package com.flab.dduikka.domain.weather.client;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.weather.domain.Weather;

public interface WeatherClient {

	Weather getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode);

}
