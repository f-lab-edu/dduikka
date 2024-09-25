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

import com.flab.dduikka.common.annotation.Member;
import com.flab.dduikka.domain.login.dto.SessionMember;
import com.flab.dduikka.domain.vote.application.VoteRecordService;
import com.flab.dduikka.domain.vote.dto.VoteRecordAddRequestDTO;
import com.flab.dduikka.domain.vote.dto.VoteRecordResponseDTO;
import com.flab.dduikka.domain.vote.dto.VoteResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

	private final VoteRecordService voteRecordService;

	@GetMapping("/{voteDate}")
	@ResponseStatus(HttpStatus.OK)
	public VoteResponseDTO findVote(@PathVariable final LocalDate voteDate) {
		return voteRecordService.findVoteTypeCount(voteDate);
	}

	@GetMapping("/record/{voteId}")
	@ResponseStatus(HttpStatus.OK)
	public VoteRecordResponseDTO findUserVoteRecord(
		@Member SessionMember member,
		@PathVariable final long voteId
	) {
		return voteRecordService.findUserVoteRecord(member.getMemberId(), voteId);
	}

	@PostMapping("/record")
	@ResponseStatus(HttpStatus.CREATED)
	public void addVoteRecord(@RequestBody @Valid final VoteRecordAddRequestDTO request) {
		voteRecordService.addVoteRecord(request);
	}

	@PatchMapping("/record/{voteRecordId}")
	@ResponseStatus(HttpStatus.OK)
	public void cancelVoteRecord(@PathVariable final long voteRecordId) {
		voteRecordService.cancelVoteRecord(voteRecordId);
	}
}
