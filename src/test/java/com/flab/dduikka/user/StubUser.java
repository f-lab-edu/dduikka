package com.flab.dduikka.user;

import com.flab.dduikka.User;

public class StubUser extends User {
	public StubUser(String userId) {
		super(userId);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
