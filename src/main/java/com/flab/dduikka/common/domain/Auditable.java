package com.flab.dduikka.common.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public abstract class Auditable {

	@NotNull
	private final LocalDateTime createdAt;

	protected Auditable(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
