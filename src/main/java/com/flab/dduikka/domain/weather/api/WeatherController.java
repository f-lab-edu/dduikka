package com.flab.dduikka.domain.weather.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.weather.application.WeatherService;
import com.flab.dduikka.domain.weather.dto.WeatherAddRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;
import com.flab.dduikka.domain.weather.facade.WeatherFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/weathers")
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;
	private final WeatherFacade weatherFacade;

	@GetMapping
	public WeatherResponseDTO findWeather(@ModelAttribute @Valid WeatherRequestDTO request) {
		return weatherService.findWeather(request);
	}

	@PostMapping
	public void addWeather(@RequestBody @Valid WeatherAddRequestDTO request) {
		weatherFacade.addWeather(request);
	}
}
