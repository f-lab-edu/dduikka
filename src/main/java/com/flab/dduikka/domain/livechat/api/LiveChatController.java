package com.flab.dduikka.domain.livechat.api;

import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.common.util.WebSocketMessageSender;
import com.flab.dduikka.domain.livechat.application.LiveChatService;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessageDTO;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponseDTO;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponseDTO;
import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.dto.SessionMember;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private static final String EVENT_TYPE = "event-type";

	private final LiveChatService liveChatService;
	private final WebSocketMessageSender webSocketMessageSender;

	@MessageMapping("/chat")
	public void sendMessage(
		@Valid @Payload final LiveChatMessageDTO request,
		SimpMessageHeaderAccessor messageHeaderAccessor
	) {
		SessionMember sessionMember =
			(SessionMember)messageHeaderAccessor.getSessionAttributes().get(SessionKey.LOGIN_USER.name());
		//header 설정
		Map<String, Object> headers = Map.of(EVENT_TYPE, EventType.CREATE.name());
		LiveChatResponseDTO response = liveChatService.createMessage(sessionMember.getMemberId(), request);
		webSocketMessageSender.sendTo("/topic/messages", response, headers);
	}

	@MessageMapping("/list/{lastMessageId}")
	public void findLiveChatList(
		@DestinationVariable final long lastMessageId,
		SimpMessageHeaderAccessor messageHeaderAccessor) {
		LiveChatsResponseDTO response = liveChatService.findAllLiveChat(lastMessageId);
		webSocketMessageSender.sendToUser(
			messageHeaderAccessor.getSessionId(),
			"/queue/chats",
			response,
			messageHeaderAccessor.getMessageHeaders()
		);
	}

	@MessageMapping("/chat/{liveChatId}")
	public void deleteMessage(
		@DestinationVariable final long liveChatId,
		SimpMessageHeaderAccessor messageHeaderAccessor
	) {
		SessionMember sessionMember =
			(SessionMember)messageHeaderAccessor.getSessionAttributes().get(SessionKey.LOGIN_USER.name());
		Map<String, Object> headers = Map.of(EVENT_TYPE, EventType.DELETE.name());
		liveChatService.deleteLiveChat(liveChatId, sessionMember.getMemberId());
		webSocketMessageSender.sendTo("/topic/messages", liveChatId, headers);
	}
}
