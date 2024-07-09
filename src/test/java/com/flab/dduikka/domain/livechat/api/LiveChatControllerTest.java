package com.flab.dduikka.domain.livechat.api;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.support.SimpAnnotationMethodMessageHandler;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.AbstractSubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.dduikka.common.util.WebSocketMessageSender;
import com.flab.dduikka.domain.livechat.application.LiveChatService;
import com.flab.dduikka.domain.livechat.domain.LiveChat;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessageDTO;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponseDTO;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponseDTO;
import com.flab.dduikka.domain.login.api.SessionKey;
import com.flab.dduikka.domain.login.dto.SessionMember;

import lombok.Getter;

@ExtendWith(MockitoExtension.class)
class LiveChatControllerTest {

	private static final String CLIENT_TO_SERVER_DESTINATION_PREFIX = "/app";
	private static final String SERVER_TO_CLIENT_DESTINATION = "/topic/messages";

	@Mock
	private LiveChatService liveChatService;

	private TestMessageChannel clientOutboundChannel;

	private TestAnnotationMethodHandler annotationMethodHandler;

	@BeforeEach
	void setUp() {
		this.clientOutboundChannel = new TestMessageChannel();
		SimpMessagingTemplate simpMessagingTemplate = new SimpMessagingTemplate(clientOutboundChannel);
		WebSocketMessageSender webSocketMessageSender = new WebSocketMessageSender(
			simpMessagingTemplate
		);
		LiveChatController controller = new LiveChatController(liveChatService, webSocketMessageSender);
		this.annotationMethodHandler = new TestAnnotationMethodHandler(
			new TestMessageChannel(), clientOutboundChannel, simpMessagingTemplate);

		this.annotationMethodHandler.registerHandler(controller);
		this.annotationMethodHandler.setDestinationPrefixes(List.of(CLIENT_TO_SERVER_DESTINATION_PREFIX));
		this.annotationMethodHandler.setMessageConverter(new MappingJackson2MessageConverter());
		this.annotationMethodHandler.setApplicationContext(new StaticApplicationContext());
		this.annotationMethodHandler.afterPropertiesSet();
	}

	@Test
	void sendMessageTest() throws JsonProcessingException {

		// given
		LiveChatMessageDTO request = new LiveChatMessageDTO("test message");
		byte[] payload = new ObjectMapper().writeValueAsBytes(request);

		LiveChatResponseDTO mockResponse =
			new LiveChatResponseDTO(1L, "encryptId", request.getText(),
				LocalDateTime.now());
		given(liveChatService.createMessage(anyLong(), any())).willReturn(mockResponse);

		StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
		headers.setDestination(CLIENT_TO_SERVER_DESTINATION_PREFIX + "/chat");
		headers.setSessionId("0");
		headers.setSessionAttributes(
			Map.of(SessionKey.LOGIN_USER.name(), new SessionMember(1L, "test@test.com")));
		Message<byte[]> message = MessageBuilder.withPayload(payload).setHeaders(headers).build();

		// when
		this.annotationMethodHandler.handleMessage(message);

		// then
		assertThat(this.clientOutboundChannel.getMessages()).hasSize(1);

		Message<?> reply = this.clientOutboundChannel.getMessages().get(0);

		StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);
		assertThat(replyHeaders.getDestination()).isEqualTo(SERVER_TO_CLIENT_DESTINATION);
		LiveChatResponseDTO responseDTO = (LiveChatResponseDTO)reply.getPayload();
		assertThat(responseDTO).isEqualTo(mockResponse);

	}

	@Test
	void findLiveChatListTest() {
		List<LiveChat> liveChats = new LinkedList<>();
		liveChats.add(new LiveChat(LocalDateTime.now(), 2L, 2L, "두번째 채팅 메세지 테스트", false));
		liveChats.add(new LiveChat(LocalDateTime.now(), 1L, 1L, "첫번째 채팅 메세지 테스트", false));

		LiveChatsResponseDTO mockResponse
			= new LiveChatsResponseDTO(
			liveChats.stream()
				.map(liveChat -> LiveChatResponseDTO.from(liveChat, "testId")).toList()
		);

		given(liveChatService.findAllLiveChat(anyLong())).willReturn(mockResponse);

		StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
		String sessionId = "1";
		headers.setDestination(CLIENT_TO_SERVER_DESTINATION_PREFIX + "/list/999999");
		headers.setSessionAttributes(
			Map.of(SessionKey.LOGIN_USER.name(), new SessionMember(1L, "test@test.com")));
		headers.setSessionId(sessionId);
		Message<byte[]> message = MessageBuilder.withPayload(new byte[0]).setHeaders(headers).build();

		// when
		this.annotationMethodHandler.handleMessage(message);

		Message<?> reply = this.clientOutboundChannel.getMessages().get(0);
		StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);

		// then
		assertThat(this.clientOutboundChannel.getMessages()).hasSize(1);
		assertThat(replyHeaders.getSessionId()).isEqualTo(sessionId);
		assertThat(replyHeaders.getDestination()).isEqualTo("/user/" + sessionId + "/queue/chats");
		assertThat(reply.getPayload()).isEqualTo(mockResponse);

	}

	@Test
	void deleteMessage() {
		// given
		long liveChatId = 1L;
		doNothing().when(liveChatService).deleteLiveChat(anyLong(), anyLong());

		StompHeaderAccessor headers = StompHeaderAccessor.create(StompCommand.SEND);
		String sessionId = "0";
		headers.setDestination(CLIENT_TO_SERVER_DESTINATION_PREFIX + "/chat/" + liveChatId);
		headers.setSessionAttributes(
			Map.of(SessionKey.LOGIN_USER.name(), new SessionMember(1L, "test@test.com")));
		headers.setSessionId(sessionId);
		Message<byte[]> message = MessageBuilder.withPayload(new byte[0]).setHeaders(headers).build();

		// when
		this.annotationMethodHandler.handleMessage(message);

		Message<?> reply = this.clientOutboundChannel.getMessages().get(0);
		StompHeaderAccessor replyHeaders = StompHeaderAccessor.wrap(reply);

		// then
		assertThat(this.clientOutboundChannel.getMessages()).hasSize(1);
		assertThat(replyHeaders.getDestination()).isEqualTo(SERVER_TO_CLIENT_DESTINATION);
		assertThat(reply.getPayload()).isEqualTo(liveChatId);
	}

	/**
	 * 핸들러로 요청을 보내는 클래스
	 */
	private static class TestAnnotationMethodHandler extends SimpAnnotationMethodMessageHandler {

		public TestAnnotationMethodHandler(SubscribableChannel inChannel, MessageChannel outChannel,
			SimpMessageSendingOperations brokerTemplate) {
			super(inChannel, outChannel, brokerTemplate);
		}

		public void registerHandler(Object handler) {
			super.detectHandlerMethods(handler);
		}
	}

	@Getter
	/**
	 * 컨트롤러로 요청되는 메세지를 감지하는 역할을 하는 클래스
	 */
	static class TestMessageChannel extends AbstractSubscribableChannel {

		private final List<Message<?>> messages = new ArrayList<>();

		@Override
		protected boolean sendInternal(Message<?> message, long timeout) {
			this.messages.add(message);
			return true;
		}
	}
}
