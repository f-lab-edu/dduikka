package com.flab.dduikka.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flab.dduikka.domain.login.exception.LoginException;
import com.flab.dduikka.domain.member.exception.MemberException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(LoginException.FailLoginException.class)
	public ResponseEntity<String> handleFailLoginException(LoginException.FailLoginException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(MemberException.DuplicatedEmailException.class)
	public ResponseEntity<String> handleDuplicatedEmailException(MemberException.DuplicatedEmailException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(MemberException.MemberNotFoundException.class)
	public ResponseEntity<String> handleNotFoundMemberException(MemberException.MemberNotFoundException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}

	@ExceptionHandler(MemberException.MemberNotJoinedException.class)
	public ResponseEntity<String> handleMemberNotJoinedException(MemberException.MemberNotJoinedException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}
