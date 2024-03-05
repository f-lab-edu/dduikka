package com.flab.dduikka.domain.livechat.api;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.livechat.application.LiveChatService;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponse;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponse;
import com.flab.dduikka.domain.livechat.dto.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LiveChatController {

	private final LiveChatService liveChatService;

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public LiveChatResponse sendMessage(
		@Payload final Message request,
		SimpMessageHeaderAccessor messageHeaderAccessor
	) {
		Long sessionId = (Long)messageHeaderAccessor.getSessionAttributes().get("sessionId");
		log.info("sessionId = {}", sessionId);
		return liveChatService.createMessage(sessionId, request);
	}

	@MessageMapping("/list/{lastMessageId}")
	@SendToUser(destinations = "/queue/chats")
	public LiveChatsResponse getLiveChatList(@DestinationVariable final long lastMessageId) {
		log.info("lastMessageId = {}", lastMessageId);
		LiveChatsResponse liveChat = liveChatService.findAllLiveChat(lastMessageId);
		log.info("LiveChatsResponse = {}", liveChat);
		return liveChat;
	}
}
