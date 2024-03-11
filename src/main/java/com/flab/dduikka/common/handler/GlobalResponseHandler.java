package com.flab.dduikka.common.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return !converterType.equals(StringHttpMessageConverter.class);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
		Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		HttpServletResponse servletResponse =
			((ServletServerHttpResponse)response).getServletResponse();
		return ResponseEntity.status(servletResponse.getStatus())
			.body(body);
	}
}
