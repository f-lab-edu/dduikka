package com.flab.dduikka.domain.weather.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "kma.value")
public class KMAWeatherProperty {
	private String serviceKey;
	private int pageNo;
	private int numOfRows;
	private String dataType;
}
