package com.flab.dduikka.common.encryption;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import com.flab.dduikka.common.domain.CacheType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EncryptedMemberIdentifierCacheTest {

	private static final String cacheName = CacheType.MEMBERIDENTIFIER_CACHE.name();

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private EncryptedMemberIdentifierCache identifierCache;

	private String getCachedMemberIdentifier(String data) {
		return cacheManager.getCache(cacheName).get(data, String.class);
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

	@Test
	@DisplayName("로컬 캐시 사용 시 암호화 연산보다 속도가 더 빠르다.")
	void whenUsingLocalCache_thenFasterThanEncryption() {
		int loopCnt = 10_000;
		String data = "1234567";

		// 로컬 캐시 사용
		long cacheStartTime = System.nanoTime();
		for (int i = 0; i < loopCnt; i++) {
			getCachedMemberIdentifier(data);
		}
		long cacheLatency = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - cacheStartTime);

		// 암호화 연산
		long encryptorStartTime = System.nanoTime();
		for (int i = 0; i < loopCnt; i++) {
			SHA256Encryptor.hashSHA256(data);
		}
		long encryptorLatency = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - encryptorStartTime);

		log.info("Test execute cache time: {}", cacheLatency);
		log.info("Test execute encrypted time: {}", encryptorLatency);
		assertThat(cacheLatency).isLessThan(encryptorLatency);
	}

	@Test
	@DisplayName("캐시 퍼포먼스 테스트")
	void cachePerfomanceTest() {
		//given
		int loopCnt = 10_000;
		String data = "1234567";
		long startTime = System.nanoTime();
		//when
		for (int i = 0; i < loopCnt; i++)
			getCachedMemberIdentifier(data);
		long latency = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
		log.info("Test execute time : {}", latency);
		//then
		assertThat(latency).isLessThanOrEqualTo(100);
	}
}
