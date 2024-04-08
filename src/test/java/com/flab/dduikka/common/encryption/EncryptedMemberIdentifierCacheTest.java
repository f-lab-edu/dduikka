package com.flab.dduikka.common.encryption;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.flab.dduikka.config.CacheConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
	CacheConfig.class,
	EncryptedMemberIdentifierCacheTest.TestConfig.class})
class EncryptedMemberIdentifierCacheTest {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private EncryptedMemberIdentifierCache identifierCache;

	private String getCachedMemberIdentifier(String data) {
		return cacheManager.getCache("MemberIdentifier").get(data, String.class);
	}

	@Test
	@DisplayName("캐싱된 회원 식별자를 요청하면 암호화된 식별자가 반환된다.")
	void whenEncryptingMemberIdThenReturnEncryptedIdentifier() {
		//given
		String memberId = "1";
		String encryptedMemberId = SHA256Encryptor.hashSHA256(memberId);

		//when
		identifierCache.cacheEncryptedMemberIdentifier(memberId);

		//then
		assertThat(encryptedMemberId).isEqualTo(getCachedMemberIdentifier(memberId));
	}

	@Test
	@DisplayName("캐싱되지 않은 회원 식별자는 null이 반환된다.")
	void whenMemberIdIsNotCachedThenReturnNull() {
		//given
		String memberId = "2";

		//when, then
		assertThat(getCachedMemberIdentifier(memberId)).isNull();
	}

	@Configuration
	static class TestConfig {
		@Bean
		public EncryptedMemberIdentifierCache encryptedMemberIdentifierCache() {
			return new EncryptedMemberIdentifierCache();
		}
	}
}
