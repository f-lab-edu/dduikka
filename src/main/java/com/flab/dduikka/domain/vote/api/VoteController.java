package com.flab.dduikka.domain.vote.api;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.vote.application.VoteService;
import com.flab.dduikka.domain.vote.dto.VoteResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

	private final VoteService voteService;

	@GetMapping("/{voteDate}")
	public ResponseEntity<VoteResponseDto> findVote(@PathVariable final LocalDate voteDate) {
		VoteResponseDto voteResponseDto = voteService.findByDate(voteDate);
		return ResponseEntity.ok().body(voteResponseDto);
	}

}
