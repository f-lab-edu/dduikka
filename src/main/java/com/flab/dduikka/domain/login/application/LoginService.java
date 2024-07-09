package com.flab.dduikka.domain.login.application;

import java.util.NoSuchElementException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.login.dto.LoginRequestDTO;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;
	private final CustomValidator validator;
	private final PasswordEncoder passwordEncoder;

	public SessionMember login(LoginRequestDTO loginRequestDto) {
		Member foundMember = memberRepository.findByEmailAndMemberStatus(loginRequestDto.getEmail())
			.orElseThrow(() -> new NoSuchElementException("해당 회원이 존재하지 않습니다."));
		validator.validateObject(foundMember);
		boolean matches = passwordEncoder.matches(loginRequestDto.getPassword(), foundMember.getPassword());
		if (!matches) {
			throw new IllegalStateException("이메일 혹은 패스워드가 맞지 않습니다.");
		}
		return SessionMember.from(foundMember);
	}
}
