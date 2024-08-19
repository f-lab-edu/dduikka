package com.flab.dduikka.domain.weather.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import com.flab.dduikka.domain.helper.ApiDocumentationHelper;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.dto.WeatherRequestDTO;
import com.flab.dduikka.domain.weather.dto.WeatherResponseDTO;

class WeatherDocumentationTest extends ApiDocumentationHelper {

	@Test
	@DisplayName("날씨를 조회한다")
	void findWeather() throws Exception {
		// given
		WeatherRequestDTO request = new WeatherRequestDTO("55", "127");
		Weather response =
			Weather.builder()
				.location(new Location(request.getLatitude(), request.getLongitude()))
				.temperature(15.3)
				.snowfall(0.0)
				.rainfall(0.0)
				.relativeHumidity(61)
				.build();
		given(weatherService.findWeather(any()))
			.willReturn(WeatherResponseDTO.from(response));

		// when
		mockMvc.perform(
				RestDocumentationRequestBuilders
					.get("/weathers?latitude={latitude}&longitude={longitude}",
						request.getLatitude(),
						request.getLongitude()))
			.andDo(print())
			.andDo(document("weathers/findWeather",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				queryParameters(
					parameterWithName("latitude").description("위도"),
					parameterWithName("longitude").description("경도")
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
