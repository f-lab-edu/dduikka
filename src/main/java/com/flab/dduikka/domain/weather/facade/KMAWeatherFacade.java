package com.flab.dduikka.domain.weather.facade;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.application.KMAWeatherClient;
import com.flab.dduikka.domain.weather.domain.KMAWeatherResultCode;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.KMAWeatherClientResponse;

import jakarta.validation.ValidationException;

@Component
@Order(value = 1)
public class KMAWeatherFacade implements WeatherFacade {

	private final KMAWeatherClient weatherClient;
	private final String serviceKey;
	private final int pageNo;
	private final int numOfRows;
	private final String dataType;

	@Autowired
	public KMAWeatherFacade(
		KMAWeatherClient weatherClient,
		@Value("#{environment['external.api-key.kma']}") String serviceKey,
		@Value("#{environment['external.variable.kma.pageNo']}") int pageNo,
		@Value("#{environment['external.variable.kma.numOfRows']}") int numOfRows,
		@Value("#{environment['external.variable.kma.dataType']}") String dataType) {
		this.weatherClient = weatherClient;
		this.serviceKey = serviceKey;
		this.pageNo = pageNo;
		this.numOfRows = numOfRows;
		this.dataType = dataType;
	}

	@Override
	public Weather getWeather(LocalDateTime dateTime, String latitude, String longitude, String cityCode) {
		validate(latitude, longitude);
		KMAWeatherClientResponse response =
			weatherClient.getWeather
				(
					serviceKey,
					pageNo,
					numOfRows,
					dataType,
					DateTimeUtil.toLocalDateString(dateTime),
					DateTimeUtil.toLocalTimeString(dateTime),
					latitude,
					longitude
				);
		KMAWeatherResultCode.checkErrorCode(response.getResponse().getHeader().getResultCode());
		return KMAWeatherClientResponse.from
			(
				response,
				new Location(latitude, longitude),
				dateTime
			);
	}

	private void validate(String latitude, String longitude) {
		if (latitude.isBlank() || longitude.isBlank())
			throw new ValidationException(String.format("blank일 수 없습니다. %s,%s", latitude, longitude));
	}
}
