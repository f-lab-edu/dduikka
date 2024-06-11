package com.flab.dduikka.domain.weather.property;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class AccuWeatherPropertyTest {

	@Test
	@DisplayName("apikey 유효성 검증 성공")
	void whenInvalidApiKey_thenContextNotFailed() {
		String apiKey = "accuWeatherKey";
		contextRunner()
			.withPropertyValues("accu.value.api-key=" + apiKey)
			.run((context) -> assertThat(context).hasNotFailed());
	}

	@DisplayName("apiKey 유효성 검증 실패")
	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void whenInvalidApiKey_thenContextFails(String apiKey) {
		contextRunner()
			.withPropertyValues("accu.value.api-key=" + apiKey) // 프로퍼티 재정의
			.run((context) -> assertThat(context).hasFailed());
	}

	@Test
	@DisplayName("language 유효성 검증 성공")
	void whenInvalidLanguage_thenContextNotFails() {
		String apiKey = "ko-kr";
		contextRunner()
			.withPropertyValues("accu.value.language=" + apiKey)
			.run((context) -> assertThat(context).hasNotFailed());
	}

	@DisplayName("language 유효성 검증 실패")
	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	void whenInvalidLanguage_thenContextFails(String language) {
		contextRunner()
			.withPropertyValues("accu.value.language=" + language)
			.run((context) -> assertThat(context).hasFailed());
	}

	private ApplicationContextRunner contextRunner() {
		return new ApplicationContextRunner()
			.withUserConfiguration(TestConfiguration.class)
			.withPropertyValues(
				"accu.value.api-key=accuWeatherKey",
				"accu.value.language=ko-kr",
				"accu.value.details=true",
				"accu.value.metric=true"
			);
	}

	@EnableConfigurationProperties(AccuWeatherProperty.class)
	static class TestConfiguration {

	}
}
