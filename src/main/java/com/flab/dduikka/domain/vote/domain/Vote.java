package com.flab.dduikka.domain.vote.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

	private Long voteId;

	@NotNull
	private LocalDate voteDate;

	@NotNull
	private LocalDateTime createdAt;

	public Vote(LocalDate voteDate) {
		this.voteDate = voteDate;
		this.createdAt = LocalDateTime.now();
	}

	@Builder
	public Vote(Long voteId, LocalDate voteDate, LocalDateTime createdAt) {
		this.voteId = voteId;
		this.voteDate = voteDate;
		this.createdAt = createdAt;
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
