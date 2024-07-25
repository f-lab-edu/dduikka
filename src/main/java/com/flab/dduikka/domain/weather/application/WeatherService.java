package com.flab.dduikka.domain.weather.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flab.dduikka.domain.weather.client.WeatherClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;
import com.flab.dduikka.domain.weather.repository.WeatherRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

	private final List<WeatherClient> weatherClients;
	private final WeatherRepository weatherRepository;

	public WeatherResponseDTO findWeather(String latitude, String longitude) {
		String geoHash = pointToGeoHash(latitude, longitude);
		Weather foundWeather = weatherRepository.findWeatherById(geoHash)
			.orElseThrow(
				() -> new WeatherException.WeatherNotFoundException("해당 위치에 대한 날씨가 존재하지 않습니다. weatherId: " + geoHash));
		return WeatherResponseDTO.from(foundWeather);
	}

	public WeatherResponseDTO getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode) {
		dateTime = dateTime.minusMinutes(10);
		for (WeatherClient weatherClient : weatherClients) {
			try {
				return WeatherResponseDTO.from(weatherClient.getWeather(dateTime, latitude, longitude, cityCode));
			} catch (FeignException | WeatherException e) {
				log.error("날씨 API 요청 중 에러 발생 : {}", e.getMessage());
			}
		}
		throw new WeatherException.ExternalAPIException("외부 API에서 오류가 발생하였습니다.");
	}

	//TODO : weather API 갱신하기 구현 시에 geohash 값 얻어오도록 함
	private String pointToGeoHash(String latitude, String longitude) {
		return "geohash temp";
	}
}
