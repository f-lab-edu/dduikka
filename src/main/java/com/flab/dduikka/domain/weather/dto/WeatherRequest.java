package com.flab.dduikka.domain.weather.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherRequest {

	private LocalDateTime requestDateTime;
	private String cityCode;
	private String latitude;
	private String longitude;
}
