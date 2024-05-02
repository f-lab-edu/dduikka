package com.flab.dduikka.domain.weather.application;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.flab.dduikka.domain.weather.dto.AccuWeatherClientResponse;

@FeignClient(name = "AccuWeatherClient", url = "${external.feign-endpoint.accu-weather}")
public interface AccuWeatherClient {

	@GetMapping("/forecasts/v1/hourly/1hour/{cityCode}")
	List<AccuWeatherClientResponse> getWeather(
		@PathVariable(value = "cityCode") String cityCode,
		@RequestParam(value = "apikey") String apikey,
		@RequestParam(value = "language") String language,
		@RequestParam(value = "details") boolean details,
		@RequestParam(value = "metric") boolean metric
	);
}
