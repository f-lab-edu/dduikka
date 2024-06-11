package com.flab.dduikka.domain.weather.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Validated
@AllArgsConstructor
@ConfigurationProperties(prefix = "accu.value")
public class AccuWeatherProperty {
	@NotBlank
	private String apiKey;
	@NotBlank
	private String language;
	private boolean details;
	private boolean metric;
}
