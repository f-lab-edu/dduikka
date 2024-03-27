package com.flab.dduikka.common.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public abstract class Auditable {

	@NotNull
	private final LocalDateTime createdAt;

	protected Auditable(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Auditable auditable = (Auditable)o;
		return Objects.equals(createdAt, auditable.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt);
	}
}
