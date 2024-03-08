package com.flab.dduikka.domain.vote.api;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(HttpStatus.OK)
	public VoteResponseDto findVote(@PathVariable final LocalDate voteDate) {
		return voteRecordService.findVoteTypeCount(voteDate);
	}

	// TODO: USER 구현 후 userId 가져오는 방법 추가
	@GetMapping("/record/{voteId}")
	@ResponseStatus(HttpStatus.OK)
	public VoteRecordResponseDto findUserVoteRecord(@PathVariable final long voteId) {
		long userId = 1L;
		return voteRecordService.findUserVoteRecord(userId, voteId);
	}

	@PostMapping("/record")
	@ResponseStatus(HttpStatus.CREATED)
	public void addVoteRecord(@RequestBody @Valid final VoteRecordAddRequestDto request) {
		voteRecordService.addVoteRecord(request);
	}

	@PatchMapping("/record/{voteRecordId}")
	@ResponseStatus(HttpStatus.OK)
	public void cancelVoteRecord(@PathVariable final long voteRecordId) {
		voteRecordService.cancelVoteRecord(voteRecordId);
	}
}
