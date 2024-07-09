package com.flab.dduikka.domain.member.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import com.flab.dduikka.common.domain.Auditable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member extends Auditable {

	private long memberId;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotNull
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

	public boolean isJoined() {
		return memberStatus.equals(MemberStatus.JOIN);
	}

	public void leave() {
		memberStatus = MemberStatus.LEAVE;
		leaveDate = LocalDate.now();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Member member = (Member)o;
		return memberId == member.memberId && Objects.equals(email, member.email) && Objects.equals(
			password, member.password) && memberStatus == member.memberStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberId, email, password, memberStatus);
	}
}
