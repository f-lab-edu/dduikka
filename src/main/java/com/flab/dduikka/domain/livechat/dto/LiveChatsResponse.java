package com.flab.dduikka.domain.livechat.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveChatsResponse {

	private List<LiveChatResponse> liveChatsResponseList;

	public static LiveChatsResponse from(List<LiveChat> liveChatList) {
		return new LiveChatsResponse(
			liveChatList.stream()
				.map(LiveChatResponse::from)
				.collect(Collectors.toList()));
	}
}
