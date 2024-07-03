package com.flab.dduikka.domain.weather.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccuWeatherClientResponseDTO {

	@JsonProperty("DateTime")
	private String dateTime;

	@JsonProperty("Temperature")
	private Temperature temperature;

	@JsonProperty("Snow")
	private Precipitation snow;

	@JsonProperty("Rain")
	private Precipitation rain;

	@JsonProperty("RelativeHumidity")
	private Integer relativeHumidity;

	public static Weather from(AccuWeatherClientResponseDTO response, Location location,
		LocalDateTime requestDateTime) {
		return Weather.builder()
			.forecastDateTime(DateTimeUtil.toLocalDateTime(response.getDateTime()))
			.temperature(response.getTemperature().getValue())
			.relativeHumidity(response.getRelativeHumidity())
			.rainfall(response.getRain().getValue())
			.snowfall(response.getSnow().getValue())
			.location(location)
			.requestDateTime(requestDateTime)
			.build();
	}

	@Getter
	@NoArgsConstructor
	public static class Temperature {
		@JsonProperty("Value")
		private Double value;
	}

	@Getter
	@NoArgsConstructor
	public static class Precipitation {
		@JsonProperty("Value")
		private Double value;
	}
}
