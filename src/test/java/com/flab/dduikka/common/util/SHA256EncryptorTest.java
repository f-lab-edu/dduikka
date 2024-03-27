package com.flab.dduikka.common.util;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class SHA256EncryptorTest {

	@Test
	@DisplayName("salting 하지 않은 암호화 결과의 동일성을 검증한다")
	void testSHA256Consistency() {
		String password = "12345";
		String initialHash = SHA256Encryptor.hashSHA256(password);

		for (int i = 1; i < 3; i++) {
			String currentHash = SHA256Encryptor.hashSHA256(password);
			assertThat(initialHash).isEqualTo(currentHash);
		}
	}

	@Test
	@DisplayName("암호화 수행시간이 1초 이하인지 검증한다")
	void testSHA256Performance() {
		//given
		int loopCnt = 50;
		String data = "1234567";
		long startTime = System.nanoTime();
		//when
		for (int i = 0; i < loopCnt; i++)
			SHA256Encryptor.hashSHA256(data);
		long latency = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
		log.info("Test execute Time : {}", latency);
		//then
		assertThat(latency).isLessThanOrEqualTo(1);
	}

}
