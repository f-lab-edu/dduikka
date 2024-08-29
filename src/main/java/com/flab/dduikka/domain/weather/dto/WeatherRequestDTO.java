package com.flab.dduikka.domain.weather.dto;

import static com.flab.dduikka.common.util.DateTimeUtil.DateTimePropery.*;
import static com.flab.dduikka.common.util.DateTimeUtil.*;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WeatherRequestDTO {

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime forecastDatetime;
	@NotBlank
	private String latitude;
	@NotBlank
	private String longitude;

	public WeatherRequestDTO(LocalDateTime forecastDatetime, String latitude, String longitude) {
		this.forecastDatetime = advanceTimeAndSetMintuesToZero(forecastDatetime, ADVANCED_TIME.getValue());
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
