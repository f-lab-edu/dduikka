package com.flab.dduikka.domain.member.api;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.member.application.MemberService;
import com.flab.dduikka.domain.member.dto.MemberRegisterRequestDto;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;

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
	public ResponseEntity<MemberResponseDto> findMember(@PathVariable final long memberId) {
		MemberResponseDto foundMember = memberService.findMember(memberId);
		return ResponseEntity.ok()
			.body(foundMember);
	}

	@GetMapping("/{email}/duplicated")
	public ResponseEntity<Boolean> isEmailDuplicated(@PathVariable @Email final String email) {
		boolean response = memberService.isEmailDuplicated(email);
		return ResponseEntity.ok()
			.body(response);
	}

	@PostMapping
	public ResponseEntity<Void> registerMember(@RequestBody @Valid MemberRegisterRequestDto request) {
		memberService.registerMember(request);
		return ResponseEntity.created(URI.create("/login")).build();
	}

}
