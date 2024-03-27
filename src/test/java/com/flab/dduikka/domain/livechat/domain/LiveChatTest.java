package com.flab.dduikka.domain.livechat.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LiveChatTest {

	@Test
	@DisplayName("채팅을 삭제 요청하면 삭제 여부는 true다")
	void whenDeleteLiveChatThenReturnsTrue() {
		//given
		LiveChat liveChat = LiveChat.builder()
			.liveChatId(1L)
			.message("채팅 메세지")
			.memberId(1L)
			.deletedFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		//when
		liveChat.delete();

		//then
		assertThat(liveChat.isDeleted()).isTrue();
	}

	@Test
	@DisplayName("채팅 삭제 요청하지 않으면 삭제 여부는 false다")
	void whenDoNotDeleteLiveChatThenReturnsFalse() {
		//given
		LiveChat liveChat = LiveChat.builder()
			.liveChatId(1L)
			.message("채팅 메세지")
			.memberId(1L)
			.deletedFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		//when, then
		assertThat(liveChat.isDeleted()).isFalse();
	}

	@Test
	@DisplayName("같은 회원 번호로 작성자 일치 여부를 조회하면 true다")
	void whenIsSameWriterThenReturnsTrue() {
		long memberId = 1L;
		//given
		LiveChat liveChat = LiveChat.builder()
			.liveChatId(1L)
			.message("채팅 메세지")
			.memberId(memberId)
			.deletedFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		//when, then
		assertThat(liveChat.isSameWriter(memberId)).isTrue();
	}

	@Test
	@DisplayName("다른 회원 번호로 작성자 일치 여부를 조회하면 true다")
	void whenIsNotSameWriterThenReturnsTrue() {
		long memberId = 1L;
		long anotherMemberId = 2L;
		//given
		LiveChat liveChat = LiveChat.builder()
			.liveChatId(1L)
			.message("채팅 메세지")
			.memberId(memberId)
			.deletedFlag(false)
			.createdAt(LocalDateTime.now())
			.build();
		//when, then
		assertThat(liveChat.isSameWriter(anotherMemberId)).isFalse();
	}

}
