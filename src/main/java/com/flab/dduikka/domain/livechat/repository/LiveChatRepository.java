package com.flab.dduikka.domain.livechat.repository;

import java.util.List;
import java.util.Optional;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

public interface LiveChatRepository {

	Optional<LiveChat> findById(long liveChatId);

	LiveChat addLiveChat(LiveChat liveChat);

	List<LiveChat> findAllLiveChat(long lastMessageId);

	void deleteLiveChat(LiveChat liveChat);
}
