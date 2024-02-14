package com.flab.dduikka.domain.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.member.application.MemberService;
import com.flab.dduikka.domain.member.dto.MemberResponseDto;

import lombok.RequiredArgsConstructor;

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

}
