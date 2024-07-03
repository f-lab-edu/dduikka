package com.flab.dduikka.domain.livechat.dto;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LiveChatMessageDTO {
	@NotBlank
	private String text;

	public LiveChatMessageDTO(String text) {
		this.text = text;
	}

	public static LiveChat to(long sessionId, LiveChatMessageDTO message) {
		return LiveChat.builder()
			.memberId(sessionId)
			.message(message.getText())
			.deletedFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
