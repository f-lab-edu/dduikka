package com.flab.dduikka.domain.livechat.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flab.dduikka.common.encryption.EncryptedMemberIdentifierCache;
import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.livechat.domain.LiveChat;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessageDTO;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponseDTO;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponseDTO;
import com.flab.dduikka.domain.livechat.exception.LiveChatException;
import com.flab.dduikka.domain.livechat.repository.LiveChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LiveChatService {

	private final LiveChatRepository liveChatRepository;
	private final EncryptedMemberIdentifierCache cachedEncryptor;
	private final CustomValidator validator;

	public LiveChatResponseDTO createMessage(long sessionId, LiveChatMessageDTO message) {
		LiveChat newLiveChat = LiveChatMessageDTO.to(sessionId, message);
		validator.validateObject(newLiveChat);
		LiveChat createdLiveChat = liveChatRepository.addLiveChat(newLiveChat);
		String encryptSessionId = cachedEncryptor.cacheEncryptedMemberIdentifier(String.valueOf(sessionId));
		return LiveChatResponseDTO.from(createdLiveChat, encryptSessionId);
	}

	public LiveChatsResponseDTO findAllLiveChat(long lastMessageId) {
		List<LiveChat> liveChats = liveChatRepository.findAllLiveChat(lastMessageId);
		return new LiveChatsResponseDTO(
			liveChats.stream()
				.map(liveChat -> LiveChatResponseDTO.from(
					liveChat,
					cachedEncryptor.cacheEncryptedMemberIdentifier(String.valueOf(liveChat.getMemberId()))
				))
				.toList());
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
