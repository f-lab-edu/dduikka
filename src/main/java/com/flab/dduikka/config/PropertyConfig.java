package com.flab.dduikka.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.flab.dduikka.domain.weather.property.AccuWeatherProperty;

@Configuration
@PropertySource(
	value =
		{
			"classpath:/property/weather/accu-weather.properties",
			"classpath:/property/weather/kma-weather.properties"
		},
	ignoreResourceNotFound = true
)
@ConfigurationPropertiesScan(basePackageClasses = AccuWeatherProperty.class)
public class PropertyConfig {
}
