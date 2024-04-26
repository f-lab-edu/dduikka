package com.flab.dduikka.domain.weather.domain;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.location.domain.Location;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Weather {

	// 예보시간, 온도, 습도, 강우량, 강설량
	private LocalDateTime forecastDateTime;
	private Location location;
	private Double temperature;
	private Integer relativeHumidity;
	private Double rainfall;
	private Double snowfall;
	private LocalDateTime requestDateTime;

	@Builder
	public Weather(LocalDateTime forecastDateTime, Location location, Double temperature, Integer relativeHumidity,
		Double rainfall, Double snowfall, LocalDateTime requestDateTime) {
		this.forecastDateTime = forecastDateTime;
		this.location = location;
		this.temperature = temperature;
		this.relativeHumidity = relativeHumidity;
		this.rainfall = rainfall;
		this.snowfall = snowfall;
		this.requestDateTime = requestDateTime;
	}
}
