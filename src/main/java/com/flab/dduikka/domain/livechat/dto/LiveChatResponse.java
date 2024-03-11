package com.flab.dduikka.domain.livechat.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveChatResponse {

	private long liveChatId;
	private String text;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalDateTime time;

	public static LiveChatResponse from(LiveChat liveChat) {
		return new LiveChatResponse(
			liveChat.getLiveChatId(),
			liveChat.getMessage(),
			liveChat.getCreatedAt()
		);
	}
}
