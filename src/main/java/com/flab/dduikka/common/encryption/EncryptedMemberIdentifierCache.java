package com.flab.dduikka.common.encryption;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class EncryptedMemberIdentifierCache {

	@Cacheable(value = "MemberIdentifier")
	public String cacheEncryptedMemberIdentifier(String data) {
		return SHA256Encryptor.hashSHA256(data);
	}
}
