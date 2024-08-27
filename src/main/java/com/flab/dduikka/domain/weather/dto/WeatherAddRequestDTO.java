package com.flab.dduikka.domain.weather.dto;

import static com.flab.dduikka.common.util.DateTimeUtil.DateTimePropery.*;
import static com.flab.dduikka.common.util.DateTimeUtil.*;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;

@Getter
public class WeatherAddRequestDTO {
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime forecastDatetime; // 현재시간
	private String latitude;
	private String longitude;
	private String cityCode;

	public WeatherAddRequestDTO(LocalDateTime forecastDatetime, String latitude, String longitude, String cityCode) {
		this.forecastDatetime = advanceTimeAndSetMintuesToZero(forecastDatetime, ADVANCED_TIME.getValue());
		this.latitude = latitude;
		this.longitude = longitude;
		this.cityCode = cityCode;
	}
}
