package com.flab.dduikka.domain.livechat.repository;

import java.util.List;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

public interface LiveChatRepository {

	LiveChat addLiveChat(LiveChat liveChat);

	List<LiveChat> findAllLiveChat(long lastMessageId);
}
