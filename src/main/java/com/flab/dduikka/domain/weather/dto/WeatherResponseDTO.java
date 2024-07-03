package com.flab.dduikka.domain.weather.dto;

import com.flab.dduikka.domain.weather.domain.Weather;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherResponseDTO {

	private Double temperature;
	private Integer relativeHumidity;
	private Double rainfall;
	private Double snowfall;

	public static WeatherResponseDTO from(Weather weather) {
		return new WeatherResponseDTO(
			weather.getTemperature(),
			weather.getRelativeHumidity(),
			weather.getRainfall(),
			weather.getSnowfall()
		);
	}
}
