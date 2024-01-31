package com.flab.dduikka.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public abstract class BaseEntity {

	@NotNull
	private final LocalDateTime createdAt;

	protected BaseEntity(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
