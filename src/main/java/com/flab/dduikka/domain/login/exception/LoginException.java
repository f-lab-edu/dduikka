package com.flab.dduikka.domain.login.exception;

public class LoginException extends RuntimeException {

	public LoginException(String message) {
		super(message);
	}

	public static class FailLoginException extends LoginException {
		public FailLoginException(String message) {
			super(message);
		}
	}

	public static class UnauthorizedAccessException extends LoginException {
		public UnauthorizedAccessException(String message) {
			super(message);
		}
	}
}
