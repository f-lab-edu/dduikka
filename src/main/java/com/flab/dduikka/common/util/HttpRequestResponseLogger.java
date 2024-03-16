package com.flab.dduikka.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpRequestResponseLogger {
	public static void logRequest(ContentCachingRequestWrapper request) {
		log.info("Request HTTPMethod = [{}], RequestURI = [{}]", request.getMethod(), request.getRequestURI());
		log.info("IP = [{}]", request.getRemoteAddr());
	}

	public static void logResponse(ContentCachingResponseWrapper response, double elapsedTime) {
		String responseBody = new String(response.getContentAsByteArray());
		if (response.getContentAsByteArray().length > 0) {
			log.info("Response: responseStatus = [{}], responseBody = [{}] ",
				HttpStatus.valueOf(response.getStatus()).name(),
				responseBody);
		} else {
			log.info("Response: responseStatus = [{}] ", HttpStatus.valueOf(response.getStatus()).name());
		}
		log.info("elapsedTime = {}", elapsedTime);
	}
}
