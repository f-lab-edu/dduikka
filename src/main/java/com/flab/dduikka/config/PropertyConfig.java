package com.flab.dduikka.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import com.flab.dduikka.domain.weather.property.AccuWeatherProperty;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = AccuWeatherProperty.class)
public class PropertyConfig {
}
