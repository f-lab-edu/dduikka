package com.flab.dduikka.domain.weather.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "accu.value")
public class AccuWeatherProperty {
	private String apiKey;
	private String language;
	private boolean details;
	private boolean metric;
}
