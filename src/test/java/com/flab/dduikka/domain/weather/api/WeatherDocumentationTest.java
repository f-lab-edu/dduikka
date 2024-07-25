package com.flab.dduikka.domain.weather.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import com.flab.dduikka.domain.helper.ApiDocumentationHelper;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;

class WeatherDocumentationTest extends ApiDocumentationHelper {

	@Test
	@DisplayName("날씨를 조회한다")
	void findWeather() throws Exception {
		// given
		LocalDateTime requestDateTime = LocalDateTime.now();
		String latitude = "55";
		String longitude = "127";
		String cityCode = "";
		Weather response =
			Weather.builder()
				.forecastDateTime(requestDateTime)
				.location(new Location(latitude, longitude))
				.temperature(15.3)
				.snowfall(0.0)
				.rainfall(0.0)
				.relativeHumidity(61)
				.build();
		given(weatherService.getWeather(any(), anyString(), anyString(), anyString()))
			.willReturn(WeatherResponseDTO.from(response));

		// when
		mockMvc.perform(
				RestDocumentationRequestBuilders
					.get("/weathers?dateTime={dateTime}&latitude={latitude}&longitude={longitude}&cityCode={cityCode}",
						requestDateTime,
						latitude,
						longitude,
						cityCode))
			.andDo(print())
			.andDo(document("weathers/findWeather",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				queryParameters(
					parameterWithName("dateTime").description("요청일시 ex) yyyy-MM-dd HH:mm:ss.SSS"),
					parameterWithName("latitude").description("위도"),
					parameterWithName("longitude").description("경도"),
					parameterWithName("cityCode").description("도시코드")
				),
				responseFields(
					fieldWithPath("headers").description("헤더"),
					fieldWithPath("body.temperature").description("온도"),
					fieldWithPath("body.relativeHumidity").description("습도"),
					fieldWithPath("body.rainfall").description("강우량"),
					fieldWithPath("body.snowfall").description("강설량"),
					fieldWithPath("statusCode").description("결과코드"),
					fieldWithPath("statusCodeValue").description("결과값")
				)))
			.andExpect(status().isOk());
	}
}
