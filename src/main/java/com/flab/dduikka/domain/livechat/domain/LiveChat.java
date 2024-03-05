package com.flab.dduikka.domain.livechat.domain;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.Auditable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LiveChat extends Auditable {

	private long liveChatId;
	@NotNull
	private long memberId;
	@NotBlank
	private String message;
	@NotNull
	private Boolean isDeleted;

	protected LiveChat(LocalDateTime createdAt) {
		super(createdAt);
	}

	@Builder
	public LiveChat(LocalDateTime createdAt, long liveChatId, long memberId, String message, Boolean isDeleted) {
		super(createdAt);
		this.liveChatId = liveChatId;
		this.memberId = memberId;
		this.message = message;
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "LiveChat{" +
			"messageId=" + liveChatId +
			", memberId=" + memberId +
			", message='" + message + '\'' +
			", isDeleted=" + isDeleted +
			'}';
	}
}
