package com.flab.dduikka.domain.livechat.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
	CREATE("생성"),
	DELETE("삭제");

	private final String desc;
}
