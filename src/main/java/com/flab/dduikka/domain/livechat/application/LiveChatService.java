package com.flab.dduikka.domain.livechat.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flab.dduikka.common.util.SHA256Encryptor;
import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.livechat.domain.LiveChat;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessage;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponse;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponse;
import com.flab.dduikka.domain.livechat.exception.LiveChatException;
import com.flab.dduikka.domain.livechat.repository.LiveChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiveChatService {

	private final LiveChatRepository liveChatRepository;
	private final SHA256Encryptor sha256Encryptor;
	private final CustomValidator validator;

	public LiveChatResponse createMessage(long sessionId, LiveChatMessage message) {
		LiveChat newLiveChat = LiveChatMessage.to(sessionId, message);
		validator.validateObject(newLiveChat);
		LiveChat createdLiveChat = liveChatRepository.addLiveChat(newLiveChat);
		String encryptSessionId = sha256Encryptor.hashSHA256(String.valueOf(sessionId));
		return LiveChatResponse.from(createdLiveChat, encryptSessionId);
	}

	public LiveChatsResponse findAllLiveChat(long lastMessageId) {
		List<LiveChat> liveChats = liveChatRepository.findAllLiveChat(lastMessageId);
		return new LiveChatsResponse(
			liveChats.stream()
				.map(liveChat -> LiveChatResponse.from(
					liveChat,
					sha256Encryptor.hashSHA256(String.valueOf(liveChat.getMemberId()))
				))
				.collect(Collectors.toList()));
	}

	public void deleteLiveChat(long liveChatId, long memberId) {
		LiveChat foundLiveChat = liveChatRepository.findById(liveChatId)
			.orElseThrow(
				() -> new LiveChatException.LiveChatNotFoundException("존재하지 않는 메세지입니다.")
			);
		validator.validateObject(foundLiveChat);
		if (foundLiveChat.isDeleted()) {
			throw new LiveChatException.LiveChatNotFoundException("존재하지 않는 메세지입니다.");
		}
		if (!foundLiveChat.isSameWriter(memberId))
			throw new LiveChatException.NonMemberAccessException("잘못된 요청입니다.");
		foundLiveChat.delete();
		liveChatRepository.deleteLiveChat(foundLiveChat);
	}
}
