package com.flab.dduikka.domain.weather.facade;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.weather.domain.Weather;

public interface WeatherFacade {

	Weather getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode);

}
