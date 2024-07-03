package com.flab.dduikka.domain.member.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/{memberId}")
	@ResponseStatus(HttpStatus.OK)
	public MemberResponseDTO findMember(@PathVariable final long memberId) {
		return memberService.findMember(memberId);
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

	@PatchMapping("/{memberId}")
	@ResponseStatus(HttpStatus.OK)
	public void leaveMember(@PathVariable final long memberId) {
		memberService.leaveMember(memberId);
	}

}
