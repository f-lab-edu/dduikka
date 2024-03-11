package com.flab.dduikka.domain.livechat.dto;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveChatMessage {
	private String text;

	public LiveChatMessage(String text) {
		this.text = text;
	}

	public static LiveChat to(long sessionId, LiveChatMessage message) {
		return LiveChat.builder()
			.memberId(sessionId)
			.message(message.getText())
			.isDeleted(false)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
