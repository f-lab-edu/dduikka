package com.flab.dduikka.domain.livechat.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.livechat.domain.LiveChat;
import com.flab.dduikka.domain.livechat.dto.LiveChatMessage;
import com.flab.dduikka.domain.livechat.dto.LiveChatResponse;
import com.flab.dduikka.domain.livechat.dto.LiveChatsResponse;
import com.flab.dduikka.domain.livechat.repository.LiveChatRepository;

@ExtendWith(MockitoExtension.class)
class LiveChatServiceTest {

	@InjectMocks
	private LiveChatService liveChatService;

	@Mock
	private LiveChatRepository liveChatRepository;

	@Mock
	private CustomValidator validator;

	@Test
	@DisplayName("메세지를 생성하면 addLiveChat을 호출한다")
	void whenCreateMessageThenCallAddLiveChat() {
		//given
		LiveChatMessage request =
			new LiveChatMessage("send Message!");

		LiveChat mockLiveChat =
			LiveChat.builder()
				.liveChatId(1L)
				.memberId(1L)
				.message(request.getText())
				.isDeleted(false)
				.createdAt(LocalDateTime.now())
				.build();

		given(liveChatRepository.addLiveChat(any()))
			.willReturn(mockLiveChat);

		//when
		LiveChatResponse createdLiveChatResponse = liveChatService.createMessage(1L, request);

		//then
		verify(liveChatRepository, times(1)).addLiveChat(any());
		assertThat(createdLiveChatResponse.getText()).isEqualTo(mockLiveChat.getMessage());
	}

	@Test
	@DisplayName("채팅 목록 조회를 요청하면 채팅 목록을 조회한다")
	void whenFindAllLiveChatThenReturnLiveChatList() {
		//given
		List<LiveChat> liveChatList
			= new ArrayList<>();
		LiveChat liveChat =
			LiveChat.builder()
				.liveChatId(1L)
				.memberId(1L)
				.message("첫번째 메세지")
				.isDeleted(false)
				.createdAt(LocalDateTime.now().minusMinutes(3))
				.build();
		LiveChat liveChat2 =
			LiveChat.builder()
				.liveChatId(2L)
				.memberId(1L)
				.message("두번째 메세지 누구 없어요?")
				.isDeleted(false)
				.createdAt(LocalDateTime.now().minusMinutes(2))
				.build();
		LiveChat liveChat3 =
			LiveChat.builder()
				.liveChatId(3L)
				.memberId(1L)
				.message("세번째 메세지 저기요")
				.isDeleted(false)
				.createdAt(LocalDateTime.now().minusMinutes(1))
				.build();
		LiveChat liveChat4 =
			LiveChat.builder()
				.liveChatId(4L)
				.memberId(3L)
				.message("네번째 ㅎㅇㅎㅇㅎㅇ")
				.isDeleted(false)
				.createdAt(LocalDateTime.now())
				.build();
		liveChatList.add(liveChat);
		liveChatList.add(liveChat2);
		liveChatList.add(liveChat3);
		liveChatList.add(liveChat4);
		given(liveChatRepository.findAllLiveChat(anyLong())).willReturn(liveChatList);

		//when, then
		LiveChatsResponse response = liveChatService.findAllLiveChat(4L);

		assertThat(response.getLiveChatsResponseList()).hasSize(4);
	}

}
