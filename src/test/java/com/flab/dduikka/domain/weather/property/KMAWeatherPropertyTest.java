package com.flab.dduikka.domain.weather.property;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class KMAWeatherPropertyTest {

	@Test
	@DisplayName("servicekey 유효성 검증 성공")
	void whenInvalidServiceKey_thenContextNotFails() {
		String apiKey = "kmaWeatherKey";
		contextRunner()
			.withPropertyValues("kma.value.service-key=" + apiKey)
			.run(context -> assertThat(context).hasNotFailed());
	}

	@ParameterizedTest
	@DisplayName("serviceKey 유효성 검증 실패")
	@ValueSource(strings = {"", " "})
	void whenInvalidServiceKey_thenContextFails(String serviceKey) {
		contextRunner()
			.withPropertyValues("kma.value.service-key=" + serviceKey)
			.run(context -> assertThat(context).hasFailed());
	}

	@Test
	@DisplayName("pageNo 유효성 검증 성공")
	void whenInvalidPageNo_thenContextNotFails() {
		int pageNo = 1;
		contextRunner()
			.withPropertyValues("kma.value.page-no=" + pageNo)
			.run(context -> assertThat(context).hasNotFailed());
	}

	@ParameterizedTest
	@DisplayName("pageNo 유효성 검증 실패")
	@ValueSource(ints = {0, -1})
	void whenInvalidPageNo_thenContextFails(int pageNo) {
		contextRunner()
			.withPropertyValues("kma.value.page-no=" + pageNo)
			.run(context -> assertThat(context).hasFailed());
	}

	@Test
	@DisplayName("numOfRows 유효성 검증 성공")
	void whenInvalidNumOfRows_thenContextNotFails() {
		int numOfRows = 1;
		contextRunner()
			.withPropertyValues("kma.value.num-of-rows=" + numOfRows)
			.run(context -> assertThat(context).hasNotFailed());
	}

	@ParameterizedTest
	@DisplayName("numOfRows 유효성 검증 실패")
	@ValueSource(ints = {0, -1})
	void whenInvalidNumOfRows_thenContextFails(int numOfRows) {
		contextRunner()
			.withPropertyValues("kma.value.num-of-rows=" + numOfRows)
			.run(context -> assertThat(context).hasFailed());
	}

	@Test
	@DisplayName("data-type 유효성 검증 성공")
	void whenInvalidDataType_thenContextNotFails() {
		String dataType = "JSON";
		contextRunner()
			.withPropertyValues("kma.value.data-type=" + dataType)
			.run(context -> assertThat(context).hasNotFailed());
	}

	@ParameterizedTest
	@DisplayName("data-type 유효성 검증 실패")
	@ValueSource(strings = {"", " "})
	void whenInvalidDataType_thenContextFails(String dataType) {
		contextRunner()
			.withPropertyValues("kma.value.data-type=" + dataType)
			.run(context -> assertThat(context).hasFailed());
	}

	private ApplicationContextRunner contextRunner() {
		return new ApplicationContextRunner()
			.withUserConfiguration(KMAWeatherPropertyTest.TestConfiguration.class)
			.withPropertyValues(
				"kma.value.service-key=KMAWeatherKey",
				"kma.value.page-no=1",
				"kma.value.num-of-rows=8",
				"kma.value.data-type=JSON"
			);
	}

	@EnableConfigurationProperties(KMAWeatherProperty.class)
	static class TestConfiguration {

	}

}
