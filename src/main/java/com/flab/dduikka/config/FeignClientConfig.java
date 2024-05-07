package com.flab.dduikka.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.flab.dduikka.domain.weather.application.AccuWeatherClient;

@Configuration
@EnableFeignClients(basePackageClasses = AccuWeatherClient.class)
public class FeignClientConfig {
}
