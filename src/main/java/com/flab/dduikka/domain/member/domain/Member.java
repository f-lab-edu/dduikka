package com.flab.dduikka.domain.member.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flab.dduikka.domain.Auditable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member extends Auditable {

	private long memberId;
	@Email
	@NotNull
	private String email;
	@NotNull
	private String password;
	private MemberStatus memberStatus;
	@PastOrPresent
	private LocalDate joinDate;
	@PastOrPresent
	private LocalDate leaveDate;

	@Builder
	public Member(long memberId, String email, String password, MemberStatus memberStatus,
		LocalDate joinDate, LocalDateTime createAt) {
		super(createAt);
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.memberStatus = memberStatus;
		this.joinDate = joinDate;
	}
}