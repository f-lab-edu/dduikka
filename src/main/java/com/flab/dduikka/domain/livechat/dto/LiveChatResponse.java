package com.flab.dduikka.domain.livechat.dto;

import java.time.format.DateTimeFormatter;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveChatResponse {

	private long liveChatId;
	private String text;
	private String time;

	public static LiveChatResponse from(LiveChat liveChat) {
		return new LiveChatResponse(
			liveChat.getLiveChatId(),
			liveChat.getMessage(),
			DateTimeFormatter.ofPattern("HH:mm").format(liveChat.getCreatedAt())
		);
	}
}
