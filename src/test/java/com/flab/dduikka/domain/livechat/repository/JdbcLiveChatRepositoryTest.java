package com.flab.dduikka.domain.livechat.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.flab.dduikka.domain.helper.IntegrationTestHelper;
import com.flab.dduikka.domain.livechat.domain.LiveChat;

class JdbcLiveChatRepositoryTest extends IntegrationTestHelper {

	private static final int CURSOR = 999999;
	@Autowired
	private LiveChatRepository liveChatRepository;

	@Test
	@DisplayName("메세지를 정상 등록하면 조회된다")
	void whenAddLiveChatThenReturnsLiveChat() {
		//given
		LiveChat newLiveChat =
			LiveChat.builder()
				.memberId(1L)
				.message("테스트용 메세지")
				.deletedFlag(false)
				.createdAt(LocalDateTime.now())
				.build();
		//when
		liveChatRepository.addLiveChat(newLiveChat);
		//then
		List<LiveChat> liveChats = liveChatRepository.findAllLiveChat(CURSOR);
		assertThat(liveChats).hasSize(1);
	}

	@Test
	@DisplayName("채팅번호로 조회하면 채팅이 조회된다")
	void whenFindByIdThenReturnsLiveChat() {
		//given
		LiveChat newLiveChat =
			LiveChat.builder()
				.memberId(1L)
				.message("테스트용 메세지")
				.deletedFlag(false)
				.createdAt(LocalDateTime.now())
				.build();
		LiveChat createdLiveChat = liveChatRepository.addLiveChat(newLiveChat);

		//when
		LiveChat foundLiveChat =
			liveChatRepository.findById(createdLiveChat.getLiveChatId()).get();

		//then
		assertThat(createdLiveChat).isEqualTo(foundLiveChat);
	}

	@Test
	@DisplayName("삭제된 채팅으로 변경하면 채팅이 삭제된다")
	void whenDeleteLiveChatThenRemovesLiveChat() {
		//given
		LiveChat newLiveChat =
			LiveChat.builder()
				.memberId(1L)
				.message("테스트용 메세지")
				.deletedFlag(false)
				.createdAt(LocalDateTime.now())
				.build();
		LiveChat createdLiveChat = liveChatRepository.addLiveChat(newLiveChat);
		createdLiveChat.delete();

		//when
		liveChatRepository.deleteLiveChat(createdLiveChat);

		//then
		LiveChat foundLiveChat =
			liveChatRepository.findById(createdLiveChat.getLiveChatId()).get();
		assertThat(createdLiveChat).isEqualTo(foundLiveChat);
	}

	@Nested
	@DisplayName("노 오프셋 페이징 테스트")
	class noOffSetPagingTest {
		@ParameterizedTest
		@ValueSource(ints = {10, 5})
		@DisplayName("pageSize 보다 크거나 같으면 pageSize만큼 조회된다")
		void whenAdd10LiveChatThenReturn10LiveChat(int len) {
			//given
			for (int i = 0; i < len; i++) {
				LiveChat liveChat = LiveChat.builder()
					.memberId(1L)
					.message("테스트용 메세지")
					.deletedFlag(false)
					.createdAt(LocalDateTime.now())
					.build();
				liveChatRepository.addLiveChat(liveChat);
			}

			//when
			List<LiveChat> liveChats = liveChatRepository.findAllLiveChat(CURSOR);

			//then
			assertThat(liveChats).hasSize(5);
		}

		@Test
		@DisplayName("메세지를 4개 등록하면 4개가 조회된다")
		void whenAdd4LiveChatThenReturn4LiveChat() {
			//given
			int len = 4;
			for (int i = 0; i < len; i++) {
				LiveChat liveChat = LiveChat.builder()
					.memberId(1L)
					.message("테스트용 메세지")
					.deletedFlag(false)
					.createdAt(LocalDateTime.now())
					.build();
				liveChatRepository.addLiveChat(liveChat);
			}

			//when
			List<LiveChat> liveChats = liveChatRepository.findAllLiveChat(CURSOR);

			//then
			assertThat(liveChats).hasSize(4);
		}
	}

}
