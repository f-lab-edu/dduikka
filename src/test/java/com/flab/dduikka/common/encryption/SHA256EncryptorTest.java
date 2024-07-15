package com.flab.dduikka.common.encryption;

import static org.assertj.core.api.Assertions.*;

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

}
