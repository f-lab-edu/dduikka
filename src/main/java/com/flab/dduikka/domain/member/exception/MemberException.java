package com.flab.dduikka.domain.member.exception;

public class MemberException extends RuntimeException {

	public MemberException(String message) {
		super(message);
	}

	public static class DuplicatedEmailException extends MemberException {
		public DuplicatedEmailException(String message) {
			super(message);
		}
	}
}
