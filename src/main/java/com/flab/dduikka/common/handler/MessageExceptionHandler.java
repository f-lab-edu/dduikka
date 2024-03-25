package com.flab.dduikka.common.handler;

import java.util.stream.Collectors;

import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.flab.dduikka.domain.livechat.exception.LiveChatException;

@ControllerAdvice
@SendToUser(destinations = "/queue/errors")
public class MessageExceptionHandler {

	@org.springframework.messaging.handler.annotation.MessageExceptionHandler(LiveChatException.class)
	public String handleNonMemberAccessException(LiveChatException e) {
		return e.getMessage();
	}

	@org.springframework.messaging.handler.annotation.MessageExceptionHandler(value = MethodArgumentNotValidException.class)
	public String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return e.getBindingResult().getAllErrors()
			.stream()
			.map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(", "));
	}

	@org.springframework.messaging.handler.annotation.MessageExceptionHandler(Exception.class)
	public String handleException(Exception e) {
		return e.getMessage();
	}

}
