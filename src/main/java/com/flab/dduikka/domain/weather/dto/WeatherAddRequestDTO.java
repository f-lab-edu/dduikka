package com.flab.dduikka.domain.weather.dto;

import static com.flab.dduikka.common.util.DateTimeUtil.DateTimePropery.*;
import static com.flab.dduikka.common.util.DateTimeUtil.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WeatherAddRequestDTO {

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asis/Seoul")
	private final LocalDateTime forecastDatetime; // 현재시간
	@NotBlank
	private final String latitude;
	@NotBlank
	private final String longitude;
	private final String cityCode;

	@JsonCreator
	public WeatherAddRequestDTO(
		@JsonProperty("forecastDatetime") LocalDateTime forecastDatetime,
		@JsonProperty("latitude") String latitude,
		@JsonProperty("longitude") String longitude,
		@JsonProperty("cityCode") String cityCode
	) {
		this.forecastDatetime = advanceTimeAndSetMintuesToZero(forecastDatetime, ADVANCED_TIME.getValue());
		this.latitude = latitude;
		this.longitude = longitude;
		this.cityCode = cityCode;
	}
}
