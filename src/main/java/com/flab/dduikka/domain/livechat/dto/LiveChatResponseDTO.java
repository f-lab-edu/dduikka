package com.flab.dduikka.domain.livechat.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveChatResponseDTO {

	private long liveChatId;
	private String eid;
	private String text;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalDateTime time;

	public static LiveChatResponseDTO from(LiveChat liveChat, String encryptId) {
		return new LiveChatResponseDTO(
			liveChat.getLiveChatId(),
			encryptId,
			liveChat.getMessage(),
			liveChat.getCreatedAt()
		);
	}
}
