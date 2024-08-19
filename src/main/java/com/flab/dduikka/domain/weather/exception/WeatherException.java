package com.flab.dduikka.domain.weather.exception;

public class WeatherException extends RuntimeException {

	public WeatherException(String message) {
		super(message);
	}

	public static class ExternalAPIException extends WeatherException {
		public ExternalAPIException(String message) {
			super(message);
		}
	}

	public static class WeatherNotFoundException extends WeatherException {
		public WeatherNotFoundException(String message) {
			super(message);
		}
	}
}
