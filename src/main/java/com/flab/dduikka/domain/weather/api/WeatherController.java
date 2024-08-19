package com.flab.dduikka.domain.weather.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.weather.application.WeatherService;
import com.flab.dduikka.domain.weather.dto.WeatherRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/weathers")
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping
	public WeatherResponseDTO findWeather(@ModelAttribute WeatherRequestDTO request) {
		return weatherService.findWeather(request);
	}
}
