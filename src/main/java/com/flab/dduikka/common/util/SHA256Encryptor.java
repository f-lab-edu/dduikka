package com.flab.dduikka.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SHA256Encryptor {

	public static String hashSHA256(String data) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("암호화 시 오류가 발생하였습니다.");
		}
		md.update(data.getBytes());
		return bytesToHex(md.digest());
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

}
