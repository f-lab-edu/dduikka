package com.flab.dduikka.domain.member.application;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flab.dduikka.common.validator.CustomValidator;
import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.dto.MemberRegisterRequestDTO;
import com.flab.dduikka.domain.member.dto.MemberResponseDTO;
import com.flab.dduikka.domain.member.exception.MemberException;
import com.flab.dduikka.domain.member.repository.MemberRepository;

@Service
public class MemberService {

	private final String passwordRegexp;
	private final MemberRepository memberRepository;
	private final CustomValidator validator;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public MemberService(
		@Value("#{environment['regexp.password']}") String passwordRegexp,
		MemberRepository memberRepository,
		CustomValidator validator, PasswordEncoder passwordEncoder) {
		this.passwordRegexp = passwordRegexp;
		this.memberRepository = memberRepository;
		this.validator = validator;
		this.passwordEncoder = passwordEncoder;
	}

	public MemberResponseDTO findMember(final long memberId) {
		Member foundUser = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException.MemberNotFoundException("해당 회원이 존재하지 않습니다. memberId: " + memberId));
		validator.validateObject(foundUser);
		if (!foundUser.isJoined()) {
			throw new IllegalStateException("탈퇴한 회원입니다. memberId:" + memberId);
		}
		return MemberResponseDTO.from(foundUser);
	}

	public boolean isEmailDuplicated(String email) {
		return memberRepository.findByEmailAndMemberStatus(email).isPresent();
	}

	public void registerMember(final MemberRegisterRequestDTO request) {
		validatePassword(request.getPassword());
		if (isEmailDuplicated(request.getEmail())) {
			throw new MemberException.DuplicatedEmailException("기등록된 회원입니다. email" + request.getEmail());
		}
		Member newMember =
			MemberRegisterRequestDTO.to(request, passwordEncoder.encode(request.getPassword()));
		validator.validateObject(newMember);
		memberRepository.addMember(newMember);
	}

	public void leaveMember(final long memberId) {
		Member foundMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException.MemberNotFoundException("해당 회원이 존재하지 않습니다. memberId: " + memberId));
		validator.validateObject(foundMember);
		if (!foundMember.isJoined()) {
			throw new MemberException.MemberNotJoinedException("이미 탈퇴하거나 탈퇴할 수 없는 회원입니다. memberId" + memberId);
		}
		foundMember.leave();
		memberRepository.leaveMember(foundMember);
	}

	private void validatePassword(String password) {
		if (!Pattern.matches(passwordRegexp, password)) {
			throw new IllegalStateException("비밀번호를 다시 입력해주세요.");
		}
	}
}
