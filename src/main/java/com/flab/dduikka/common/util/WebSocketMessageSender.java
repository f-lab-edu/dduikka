package com.flab.dduikka.common.util;

import java.util.Map;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketMessageSender {

	private final SimpMessagingTemplate simpMessagingTemplate;

	public void sendTo(String destination, Object payload, Map<String, Object> headers) {
		simpMessagingTemplate.convertAndSend(destination, payload, headers);
	}

	public void sendTo(String destination, Object payload) {
		simpMessagingTemplate.convertAndSend(destination, payload);
	}

	public void sendToUser(String sessionId, String destination, Object payload, Map<String, Object> headers) {
		simpMessagingTemplate.convertAndSendToUser(sessionId, destination, payload, headers);
	}

	public void sendToUser(String sessionId, String destination, Object payload) {
		simpMessagingTemplate.convertAndSendToUser(sessionId, destination, payload);
	}
}
