package com.flab.dduikka.domain.weather.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.flab.dduikka.common.domain.Auditable;
import com.flab.dduikka.domain.location.domain.Location;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Weather extends Auditable {

	// 예보시간, 온도, 습도, 강우량, 강설량
	private String weatherId;
	private LocalDateTime forecastDateTime;
	private Location location;
	private Double temperature;
	private Integer relativeHumidity;
	private Double rainfall;
	private Double snowfall;

	@Builder
	public Weather(LocalDateTime createdAt, String weatherId, LocalDateTime forecastDateTime, Location location,
		Double temperature, Integer relativeHumidity, Double rainfall, Double snowfall) {
		super(createdAt);
		this.weatherId = weatherId;
		this.forecastDateTime = forecastDateTime;
		this.location = location;
		this.temperature = temperature;
		this.relativeHumidity = relativeHumidity;
		this.rainfall = rainfall;
		this.snowfall = snowfall;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Weather weather = (Weather)o;
		return Objects.equals(weatherId, weather.weatherId) && Objects.equals(forecastDateTime,
			weather.forecastDateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(weatherId, forecastDateTime);
	}

	@Override
	public String toString() {
		return "Weather{" +
			"weatherId='" + weatherId + '\'' +
			", forecastDateTime=" + forecastDateTime +
			", location=" + location +
			", temperature=" + temperature +
			", relativeHumidity=" + relativeHumidity +
			", rainfall=" + rainfall +
			", snowfall=" + snowfall +
			'}';
	}
}
