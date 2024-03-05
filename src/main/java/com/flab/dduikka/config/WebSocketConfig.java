package com.flab.dduikka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.flab.dduikka.common.interceptor.HttpHandShakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker   // STOMP 사용
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) { // 메모리 기반 message broker 활성화
		registry.enableSimpleBroker("/topic", "/queue"); //구독경로 topic(모든 사용자), queue(특정)
		registry.setApplicationDestinationPrefixes("/app"); //메세지 발송
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry
			.addEndpoint("/chats")
			.addInterceptors(new HttpHandShakeInterceptor())
			.setAllowedOriginPatterns("*").withSockJS();
	}

}
