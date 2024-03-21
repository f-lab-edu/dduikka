package com.flab.dduikka.domain.livechat.dto;

import static com.flab.dduikka.common.util.SHA256Encryptor.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveChatResponse {

	private long liveChatId;
	private String eid;
	private String text;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalDateTime time;

	public static LiveChatResponse from(LiveChat liveChat) {
		return new LiveChatResponse(
			liveChat.getLiveChatId(),
			hashSHA256(String.valueOf(liveChat.getMemberId())),
			liveChat.getMessage(),
			liveChat.getCreatedAt()
		);
	}
}
