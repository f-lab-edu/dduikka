package com.flab.dduikka.domain.weather.dto;

import static com.flab.dduikka.common.util.DateTimeUtil.DateTimePropery.*;
import static com.flab.dduikka.common.util.DateTimeUtil.*;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeatherRequestDTO {
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime forecastDatetime;
	private String latitude;
	private String longitude;

	public WeatherRequestDTO(LocalDateTime forecastDatetime, String latitude, String longitude) {
		this.forecastDatetime = advanceTimeAndSetMintuesToZero(forecastDatetime, ADVANCED_TIME.getValue());
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
