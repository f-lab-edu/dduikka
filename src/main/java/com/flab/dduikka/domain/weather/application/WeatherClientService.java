package com.flab.dduikka.domain.weather.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.weather.client.WeatherClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherAddRequestDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeatherClientService {

	private final List<WeatherClient> weatherClients;

	@Autowired
	public WeatherClientService(List<WeatherClient> weatherClients) {
		this.weatherClients = weatherClients;
	}

	public Weather getWeather(WeatherAddRequestDTO request) {
		for (WeatherClient weatherClient : weatherClients) {
			try {
				return weatherClient.getWeather
					(
						request.getForecastDatetime(),
						request.getLatitude(),
						request.getLongitude(),
						request.getCityCode()
					);
			} catch (FeignException | WeatherException e) {
				log.error("날씨 API 요청 중 에러 발생 : {}", e.getMessage());
			}
		}
		throw new WeatherException.ExternalAPIException("외부 API에서 오류가 발생하였습니다.");
	}
}
