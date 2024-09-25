package com.flab.dduikka.domain.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.common.annotation.Member;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.member.application.MemberService;
import com.flab.dduikka.domain.member.dto.MemberRegisterRequestDTO;
import com.flab.dduikka.domain.member.dto.MemberResponseDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	public MemberResponseDTO findMember(@Member SessionMember member) {
		return memberService.findMember(member.getMemberId());
	}

	@GetMapping("/{email}/duplicated")
	@ResponseStatus(HttpStatus.OK)
	public Boolean isEmailDuplicated(@PathVariable @Email final String email) {
		return memberService.isEmailDuplicated(email);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void registerMember(@RequestBody @Valid MemberRegisterRequestDTO request) {
		memberService.registerMember(request);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public void leaveMember(@Member SessionMember member) {
		memberService.leaveMember(member.getMemberId());
	}

}
