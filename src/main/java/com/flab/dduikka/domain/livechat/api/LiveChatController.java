package com.flab.dduikka.domain.livechat.api;

import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.livechat.application.LiveChatService;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessage;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponse;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponse;
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
	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat")
	public void sendMessage(
		@Valid @Payload final LiveChatMessage request,
		SimpMessageHeaderAccessor messageHeaderAccessor
	) {
		SessionMember sessionMember =
			(SessionMember)messageHeaderAccessor.getSessionAttributes().get(SessionKey.LOGIN_USER.name());
		LiveChatResponse liveChatResponse = liveChatService.createMessage(sessionMember.getMemberId(), request);
		//header 설정
		Map<String, Object> headers = Map.of(EVENT_TYPE, EventType.CREATE.name());
		simpMessagingTemplate.convertAndSend("/topic/messages", liveChatResponse,
			headers);
	}

	@MessageMapping("/list/{lastMessageId}")
	public void getLiveChatList(
		@DestinationVariable final long lastMessageId,
		SimpMessageHeaderAccessor messageHeaderAccessor) {
		LiveChatsResponse liveChat = liveChatService.findAllLiveChat(lastMessageId);
		simpMessagingTemplate.convertAndSendToUser(
			messageHeaderAccessor.getSessionId(),
			"/queue/chats",
			liveChat,
			messageHeaderAccessor.getMessageHeaders());
	}

	@MessageMapping("/chat/{liveChatId}")
	public void deleteMessage(
		@DestinationVariable final long liveChatId,
		SimpMessageHeaderAccessor messageHeaderAccessor
	) {
		SessionMember sessionMember =
			(SessionMember)messageHeaderAccessor.getSessionAttributes().get(SessionKey.LOGIN_USER.name());
		liveChatService.deleteLiveChat(liveChatId, sessionMember.getMemberId());
		Map<String, Object> headers = Map.of(EVENT_TYPE, EventType.DELETE.name());
		simpMessagingTemplate.convertAndSend("/topic/messages", liveChatId, headers);
	}
}
