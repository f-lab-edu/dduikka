package com.flab.dduikka.common.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.exception.LoginException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomHttpSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {
		super.beforeHandshake(request, response, wsHandler, attributes);
		Object session = attributes.get(SessionKey.LOGIN_USER.name());

		if (session == null) {
			throw new LoginException("로그인이 필요합니다.");
		}
		return true;
	}
}
