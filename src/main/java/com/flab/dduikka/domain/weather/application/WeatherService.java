package com.flab.dduikka.domain.weather.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flab.dduikka.common.util.GeoHashUtil;
import com.flab.dduikka.domain.weather.client.WeatherClient;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;
import com.flab.dduikka.domain.weather.repository.WeatherRepository;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeatherService {

	private final Integer advanceMinutes;
	private final List<WeatherClient> weatherClients;
	private final WeatherRepository weatherRepository;

	@Autowired
	public WeatherService
		(
			@Value("${weather.advance-minutes}") Integer advanceMinutes,
			List<WeatherClient> weatherClients,
			WeatherRepository weatherRepository
		) {
		this.advanceMinutes = advanceMinutes;
		this.weatherClients = weatherClients;
		this.weatherRepository = weatherRepository;
	}

	public WeatherResponseDTO findWeather(WeatherRequestDTO request) {
		String geoHash = GeoHashUtil.getGeoHashString(request.getLatitude(), request.getLongitude());
		LocalDateTime advancedForecastRequestTime = advanceForecastRequestTime(request.getForecastDatetime());
		Weather foundWeather = weatherRepository.findWeatherByIdAndForecastDatetime(geoHash,
				advancedForecastRequestTime)
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

	/**
	 * 기상청 api 기준으로 발표 시간 딜레이가 존재한다.
	 * n분을 앞당겨 발표가 딜레이 되어도 조회할 수 있도록 변경한다.
	 *
	 * @param requestTime
	 * @return LocalDateTime
	 */
	private LocalDateTime advanceForecastRequestTime(LocalDateTime requestTime) {
		return requestTime.minusMinutes(advanceMinutes);
	}
}
