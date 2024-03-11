package com.flab.dduikka.domain.livechat.domain;

import java.time.LocalDateTime;

import com.flab.dduikka.domain.Auditable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

public class LiveChat extends Auditable {

	@Getter
	private long liveChatId;
	@Getter
	@NotNull
	private long memberId;
	@Getter
	@NotBlank
	private String message;
	@NotNull
	private boolean isDeleted;

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

	// BeanPropertySqlParameterSource로 변환 시 변수를 delete로 인식하는 문제가 있어, 별도로 정의한다.
	public boolean getIsDeleted() {
		return isDeleted;
	}
}
