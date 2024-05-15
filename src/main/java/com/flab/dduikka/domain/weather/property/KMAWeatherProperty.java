package com.flab.dduikka.domain.weather.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Validated
@AllArgsConstructor
@ConfigurationProperties(prefix = "kma.value")
public class KMAWeatherProperty {
	@NotBlank
	private String serviceKey;
	@Positive
	private int pageNo;
	@Positive
	private int numOfRows;
	@NotBlank
	private String dataType;
}
