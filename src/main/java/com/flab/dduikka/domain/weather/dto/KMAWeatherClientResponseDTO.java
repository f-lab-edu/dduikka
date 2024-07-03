package com.flab.dduikka.domain.weather.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.dduikka.common.util.DateTimeUtil;
import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;
import com.flab.dduikka.domain.weather.domain.WeatherCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KMAWeatherClientResponseDTO {

	private WeatherPayload response;

	public static Weather from
		(
			KMAWeatherClientResponseDTO response,
			Location location,
			LocalDateTime requestDateTime
		) {
		double temperature = 0.0;
		double precipitation = 0.0;
		int relativeHumidity = 0;
		boolean isRainFall = false;
		List<WeatherPayload.WeatherBody.WeatherItems.WeatherItem> weatherItems
			= response.getResponse().getBody().getItems().getItem();
		for (WeatherPayload.WeatherBody.WeatherItems.WeatherItem item : weatherItems) {
			switch (item.category) {
				case T1H:
					temperature = Double.parseDouble(item.obsrValue);
					break;
				case RN1:
					precipitation = Double.parseDouble(item.obsrValue);
					break;
				case REH:
					relativeHumidity = Integer.parseInt(item.obsrValue);
					break;
				case PTY:
					if (item.obsrValue.equals("3"))
						isRainFall = true;
					break;
				default:
			}
		}
		return Weather.builder()
			.forecastDateTime
				(
					DateTimeUtil.toLocalDateTime(weatherItems.get(0).baseDate, weatherItems.get(0).baseTime)
				)
			.temperature(temperature)
			.relativeHumidity(relativeHumidity)
			.rainfall(isRainFall ? precipitation : 0.0)
			.snowfall(!isRainFall ? precipitation : 0.0)
			.location(location)
			.requestDateTime(requestDateTime)
			.build();
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class WeatherPayload {
		private WeatherHeader header;
		private WeatherBody body;

		@Getter
		@AllArgsConstructor
		@NoArgsConstructor
		public static class WeatherHeader {
			private String resultCode;
			private String resultMsg;
		}

		@Getter
		@AllArgsConstructor
		@NoArgsConstructor
		public static class WeatherBody {
			private WeatherItems items;

			@Getter
			@AllArgsConstructor
			@NoArgsConstructor
			public static class WeatherItems {
				private List<WeatherItem> item;

				@Getter
				@NoArgsConstructor
				public static class WeatherItem {
					private WeatherCode category;
					private String baseDate;
					private String baseTime;
					private String obsrValue;
					private double nx;
					private double ny;

					@JsonCreator
					public WeatherItem(
						@JsonProperty("category") String category,
						@JsonProperty("baseDate") String baseDate,
						@JsonProperty("baseTime") String baseTime,
						@JsonProperty("obsrValue") String obsrValue,
						@JsonProperty("nx") double nx,
						@JsonProperty("ny") double ny) {
						this.category = WeatherCode.stringToWeatherCode(category);
						this.baseDate = baseDate;
						this.baseTime = baseTime;
						this.obsrValue = obsrValue;
						this.nx = nx;
						this.ny = ny;
					}

					@Override
					public String toString() {
						return "Weather{" +
							"category=" + category.toString() +
							", baseDate='" + baseDate + '\'' +
							", baseTime='" + baseTime + '\'' +
							", obsrValue='" + obsrValue + '\'' +
							", nx=" + nx +
							", ny=" + ny +
							'}';
					}
				}
			}
		}
	}
}
