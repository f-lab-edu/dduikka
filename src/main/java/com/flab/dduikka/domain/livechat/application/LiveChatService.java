package com.flab.dduikka.domain.livechat.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.livechat.domain.LiveChat;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessage;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponse;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponse;
import com.flab.dduikka.domain.livechat.repository.LiveChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiveChatService {

	private final LiveChatRepository liveChatRepository;
	private final CustomValidator validator;

	public LiveChatResponse createMessage(long sessionId, LiveChatMessage message) {
		LiveChat newLiveChat = LiveChatMessage.to(sessionId, message);
		validator.validateObject(newLiveChat);
		LiveChat createdLiveChat = liveChatRepository.addLiveChat(newLiveChat);
		return LiveChatResponse.from(createdLiveChat);
	}

	public LiveChatsResponse findAllLiveChat(long lastMessageId) {
		List<LiveChat> liveChat = liveChatRepository.findAllLiveChat(lastMessageId);
		return LiveChatsResponse.from(liveChat);
	}
}
