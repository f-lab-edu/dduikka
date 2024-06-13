package com.flab.dduikka.domain.livechat.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import com.flab.dduikka.common.domain.Auditable;

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
	private boolean deletedFlag;

	protected LiveChat(LocalDateTime createdAt) {
		super(createdAt);
	}

	@Builder
	public LiveChat(LocalDateTime createdAt, long liveChatId, long memberId, String message, boolean deletedFlag) {
		super(createdAt);
		this.liveChatId = liveChatId;
		this.memberId = memberId;
		this.message = message;
		this.deletedFlag = deletedFlag;
	}

	public void delete() {
		this.deletedFlag = true;
	}

	public boolean isSameWriter(long memberId) {
		return this.memberId == memberId;
	}

	public boolean isDeleted() {
		return this.deletedFlag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LiveChat liveChat = (LiveChat)o;
		return liveChatId == liveChat.liveChatId && memberId == liveChat.memberId && deletedFlag == liveChat.deletedFlag
			&& Objects.equals(message, liveChat.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(liveChatId, memberId, message, deletedFlag);
	}

	@Override
	public String toString() {
		return "LiveChat{" +
			"messageId=" + liveChatId +
			", memberId=" + memberId +
			", message='" + message + '\'' +
			", isDeleted=" + deletedFlag +
			'}';
	}
}
