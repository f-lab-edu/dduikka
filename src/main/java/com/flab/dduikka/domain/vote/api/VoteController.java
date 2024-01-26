package com.flab.dduikka.domain.vote.api;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flab.dduikka.domain.vote.application.VoteRecordService;
import com.flab.dduikka.domain.vote.dto.VoteRecordAddRequestDto;
import com.flab.dduikka.domain.vote.dto.VoteRecordResponseDto;
import com.flab.dduikka.domain.vote.dto.VoteResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

	private final VoteRecordService voteRecordService;

	@GetMapping("/{voteDate}")
	public ResponseEntity<VoteResponseDto> findVote(@PathVariable final LocalDate voteDate) {
		VoteResponseDto voteResponseDto = voteRecordService.findVoteTypeCount(voteDate);
		return ResponseEntity.ok()
			.body(voteResponseDto);
	}

	// TODO: USER 구현 후 userId 가져오는 방법 추가
	@GetMapping("/record/{voteId}")
	public ResponseEntity<VoteRecordResponseDto> findUserVoteRecord(@PathVariable final long voteId) {
		long userId = 1L;
		VoteRecordResponseDto voteRecordResponseDto = voteRecordService
			.findUserVoteRecord(userId, voteId);
		return ResponseEntity.ok()
			.body(voteRecordResponseDto);
	}

	@PostMapping("/record")
	public ResponseEntity<Void> addVoteRecord(@RequestBody @Valid final VoteRecordAddRequestDto request) {
		voteRecordService.addVoteRecord(request);
		return ResponseEntity.created(URI.create("/vote/record/" + LocalDate.now()))
			.build();
	}

	@PatchMapping("/record/{voteRecordId}")
	public ResponseEntity<Void> cancelVoteRecord(@PathVariable final long voteRecordId) {
		voteRecordService.cancelVoteRecord(voteRecordId);
		return ResponseEntity.ok()
			.build();
	}
}
