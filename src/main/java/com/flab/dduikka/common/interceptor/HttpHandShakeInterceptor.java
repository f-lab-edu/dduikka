package com.flab.dduikka.common.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.login.exception.LoginException;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

// websocket handshake 과정의 interceptor
@Slf4j
public class HttpHandShakeInterceptor implements HandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {

		ServletServerHttpRequest httpRequest = (ServletServerHttpRequest)request;
		HttpSession session =
			httpRequest.getServletRequest().getSession(false);

		log.info("Session = {}", session);

		// HTTP 통신의 session을 웹소켓 세션에 등록한다
		if (session != null) {
			SessionMember sessionMember
				= (SessionMember)session.getAttribute(SessionKey.LOGIN_USER.name());
			attributes.put("sessionId", sessionMember.getMemberId());
			log.info("sessionId = {}", sessionMember.getMemberId());
			return true;
		}
		throw new LoginException("로그인이 필요합니다.");
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Exception exception) {
		// comment explaining why the method is empty
	}
}
