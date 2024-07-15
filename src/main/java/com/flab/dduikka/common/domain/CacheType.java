package com.flab.dduikka.common.domain;

import lombok.Getter;

@Getter
public enum CacheType {
	MEMBERIDENTIFIER_CACHE(0, 500, 10 * 60);

	private final int expireAfterWrite;
	private final int maximumSize;
	private final int expireAfterAccess;

	CacheType(int expireAfterWrite, int maximumSize, int expireAfterAccess) {
		this.expireAfterWrite = expireAfterWrite;
		this.maximumSize = maximumSize;
		this.expireAfterAccess = expireAfterAccess;
	}
}
