package com.flab.dduikka.domain.weather.api;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.weather.application.WeatherService;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/weathers")
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping
	public WeatherResponseDTO findWeather(
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime dateTime,
		@RequestParam String latitude,
		@RequestParam String longitude,
		@RequestParam String cityCode
	) {
		return weatherService.getWeather(dateTime, latitude, longitude, cityCode);
	}
}
