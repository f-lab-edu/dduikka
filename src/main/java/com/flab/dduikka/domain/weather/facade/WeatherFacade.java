package com.flab.dduikka.domain.weather.facade;

import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.weather.application.WeatherClientService;
import com.flab.dduikka.domain.weather.application.WeatherService;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherAddRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherFacade {

	private final WeatherService weatherService;
	private final WeatherClientService weatherClientService;

	public void addWeather(WeatherAddRequestDTO request) {
		// 현재 시간, 위치 기반으로 날씨 데이터를 조회한다.
		if (!weatherService.existWeather(request)) {
			// 외부 API를 통해 실시간 예보 데이터를 조회한다.
			Weather getWeather = weatherClientService.getWeather(request);
			// 예보 데이터를 등록한다.
			weatherService.addWeather(getWeather);
		}
	}
}
