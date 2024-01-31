package com.flab.dduikka.domain.vote.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import com.flab.dduikka.domain.BaseEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Vote extends BaseEntity {

	private Long voteId;

	@NotNull
	private LocalDate voteDate;

	private Vote(LocalDateTime createdAt) {
		super(createdAt);
	}

	public Vote(LocalDate voteDate) {
		super(LocalDateTime.now());
		this.voteDate = voteDate;
	}

	@Builder
	public Vote(Long voteId, LocalDate voteDate, LocalDateTime createdAt) {
		super(createdAt);
		this.voteId = voteId;
		this.voteDate = voteDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Vote vote = (Vote)o;
		return Objects.equals(voteId, vote.voteId) && Objects.equals(voteDate, vote.voteDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(voteId, voteDate);
	}
}
