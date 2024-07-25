package com.flab.dduikka.domain.weather.client;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.KMAWeatherResultCode;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.KMAWeatherClientResponseDTO;
import com.flab.dduikka.domain.weather.property.KMAWeatherProperty;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Component
@Order(value = 1)
@RequiredArgsConstructor
public class KMAWeatherClient implements WeatherClient {

	private final KMAWeatherFeignClient weatherFeignClient;
	private final KMAWeatherProperty kmaWeatherProperty;

	@Override
	public Weather getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode) {
		validate(latitude, longitude);
		KMAWeatherClientResponseDTO response =
			weatherFeignClient.getWeather
				(
					kmaWeatherProperty.getServiceKey(),
					kmaWeatherProperty.getPageNo(),
					kmaWeatherProperty.getNumOfRows(),
					kmaWeatherProperty.getDataType(),
					DateTimeUtil.toLocalDateString(dateTime),
					DateTimeUtil.toLocalTimeString(dateTime),
					latitude,
					longitude
				);
		KMAWeatherResultCode.checkErrorCode(response.getResponse().getHeader().getResultCode());
		return KMAWeatherClientResponseDTO.from
			(
				response,
				new Location(latitude, longitude)
			);
	}

	private void validate(String latitude, String longitude) {
		if (latitude.isBlank() || longitude.isBlank())
			throw new ValidationException(String.format("blank일 수 없습니다. %s,%s", latitude, longitude));
	}
}
