package com.flab.dduikka.domain.weather.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Weather weather = (Weather)o;
		return Objects.equals(forecastDateTime, weather.forecastDateTime) && Objects.equals(location,
			weather.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(forecastDateTime, location);
	}

	@Override
	public String toString() {
		return "Weather{" +
			"forecastDateTime=" + forecastDateTime +
			", location=" + location +
			", temperature=" + temperature +
			", relativeHumidity=" + relativeHumidity +
			", rainfall=" + rainfall +
			", snowfall=" + snowfall +
			", requestDateTime=" + requestDateTime +
			'}';
	}
}
