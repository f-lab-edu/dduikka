package com.flab.dduikka.domain.weather.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.weather.dto.WeatherResponse;
import com.flab.dduikka.domain.weather.exception.WeatherException;
import com.flab.dduikka.domain.weather.facade.WeatherClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

	private final List<WeatherClient> weatherClients;

	public WeatherResponse getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode) {
		dateTime = dateTime.minusMinutes(10);
		for (WeatherClient weatherClient : weatherClients) {
			try {
				return WeatherResponse.from(weatherClient.getWeather(dateTime, latitude, longitude, cityCode));
			} catch (FeignException | WeatherException e) {
				log.error("날씨 API 요청 중 에러 발생 : {}", e.getMessage());
			}
		}
		throw new WeatherException.ExternalAPIException("외부 API에서 오류가 발생하였습니다.");
	}
}
