package com.flab.dduikka.domain.livechat.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LiveChatsResponseDTO {

	private List<LiveChatResponseDTO> liveChatsResponseList;

}
