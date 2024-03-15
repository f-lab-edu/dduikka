package com.flab.dduikka.common.filter;

import static com.flab.dduikka.common.util.LogFormatter.*;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
		IOException {

		ContentCachingRequestWrapper httpServletRequest =
			new ContentCachingRequestWrapper((HttpServletRequest)servletRequest);
		ContentCachingResponseWrapper httpServletResponse =
			new ContentCachingResponseWrapper((HttpServletResponse)servletResponse);

		long startTime = System.nanoTime();

		try {
			logRequest(httpServletRequest);
			filterChain.doFilter(httpServletRequest, httpServletResponse);

		} catch (Exception e) {
			log.error("{}", e.getMessage());
		} finally {
			long endTime = System.nanoTime();
			logResponse(httpServletResponse, (endTime - startTime) / 1_000_000_000.0);
			httpServletResponse.copyBodyToResponse();
		}
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}
}
