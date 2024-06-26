package com.flab.dduikka.domain.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.core.io.ResourceLoader.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import com.github.tomakehurst.wiremock.WireMockServer;

import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource(
	locations =
		{
			"classpath:/property/weather/accu-weather.properties",
			"classpath:/property/weather/kma-weather.properties"
		}
)
public abstract class WireMockServerHelper {

	protected static String KMA_PATH = "/getUltraSrtNcst";
	protected static String ACCU_WEATHER_PATH = "/forecasts/v1/hourly/1hour";
	private static String CONTENT_TYPE = "Content-Type";
	private static String CONTENT_VALEU = "application/json";
	@Autowired
	protected ResourceLoader resourceLoader;

	@Autowired
	protected WireMockServer wireMockServer;

	protected JsonNode readJsonFile(String path) throws IOException {
		Resource resource = resourceLoader.getResource(CLASSPATH_URL_PREFIX + path);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(resource.getInputStream());
	}

	protected String createUriString(String path, MultiValueMap<String, String> params) {
		UriBuilderFactory factory = new DefaultUriBuilderFactory();
		return factory.uriString(path)
			.queryParams(params).build().toString();
	}

	public static class Mocks {
		public static void setupMockWeatherResponse(WireMockServer server, String url, JsonNode node, int status) throws
			IOException {
			server.stubFor(
				get(urlEqualTo(url))
					.willReturn(
						aResponse().withStatus(status)
							.withHeader(CONTENT_TYPE, CONTENT_VALEU)
							.withJsonBody(node)));
		}
	}
}
