package com.flab.dduikka.domain.weather.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flab.dduikka.common.util.GeoHashUtil;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherAddRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;
import com.flab.dduikka.domain.weather.exception.WeatherException;
import com.flab.dduikka.domain.weather.repository.WeatherRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

	private final WeatherRepository weatherRepository;

	@Transactional(readOnly = true)
	public WeatherResponseDTO findWeather(WeatherRequestDTO request) {
		String geoHash = GeoHashUtil.getGeoHashString(request.getLatitude(), request.getLongitude());
		Weather foundWeather = weatherRepository.findWeatherByIdAndForecastDatetime(geoHash,
				request.getForecastDatetime())
			.orElseThrow(
				() -> new WeatherException.WeatherNotFoundException("해당 위치에 대한 날씨가 존재하지 않습니다. weatherId: " + geoHash));
		return WeatherResponseDTO.from(foundWeather);
	}

	@Transactional(readOnly = true)
	public boolean existWeather(WeatherAddRequestDTO request) {
		String geoHash = GeoHashUtil.getGeoHashString(request.getLatitude(), request.getLongitude());
		return weatherRepository.findWeatherByIdAndForecastDatetime(geoHash, request.getForecastDatetime()).isPresent();
	}

	@Transactional
	public void addWeather(Weather weather) {
		weatherRepository.addWeather(weather);
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
