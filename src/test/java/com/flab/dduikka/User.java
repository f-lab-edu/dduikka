package com.flab.dduikka;

public class User {

	private String userId;

	public User(String userId) {
		this.userId = userId;
	}

	private User() {
	}

	@Override
	public String toString() {
		return userId;
	}
}
