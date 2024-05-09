package com.flab.dduikka.domain.weather.dto;

import com.flab.dduikka.domain.weather.domain.Weather;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherResponse {

	private Double temperature;
	private Integer relativeHumidity;
	private Double rainfall;
	private Double snowfall;

	public static WeatherResponse from(Weather weather) {
		return new WeatherResponse(
			weather.getTemperature(),
			weather.getRelativeHumidity(),
			weather.getRainfall(),
			weather.getSnowfall()
		);
	}
}
