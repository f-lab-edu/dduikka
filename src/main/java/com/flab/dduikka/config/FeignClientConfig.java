package com.flab.dduikka.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import com.flab.dduikka.domain.weather.client.AccuWeatherFeignClient;

@Configuration
@EnableFeignClients(basePackageClasses = AccuWeatherFeignClient.class)
public class FeignClientConfig {
}
