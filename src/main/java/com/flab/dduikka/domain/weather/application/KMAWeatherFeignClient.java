package com.flab.dduikka.domain.weather.application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flab.dduikka.domain.weather.dto.KMAWeatherClientResponse;

@FeignClient(name = "KMAWeatherClient", url = "${external.feign-endpoint.kma}")
public interface KMAWeatherFeignClient {

	@GetMapping(value = "/getUltraSrtNcst")
	KMAWeatherClientResponse getWeather(
		@RequestParam("serviceKey") String serviceKey,
		@RequestParam("pageNo") int pageNo,
		@RequestParam("numOfRows") int numOfRows,
		@RequestParam("dataType") String dataType,
		@RequestParam("base_date") String baseDate,
		@RequestParam("base_time") String baseTime,
		@RequestParam("nx") String nx,
		@RequestParam("ny") String ny
	);
}
