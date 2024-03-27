package com.flab.dduikka.domain.livechat.exception;

public class LiveChatException extends RuntimeException {

	public LiveChatException(String message) {
		super(message);
	}

	public static class LiveChatNotFoundException extends LiveChatException {
		public LiveChatNotFoundException(String message) {
			super(message);
		}
	}

	public static class NonMemberAccessException extends LiveChatException {
		public NonMemberAccessException(String message) {
			super(message);
		}
	}
}
