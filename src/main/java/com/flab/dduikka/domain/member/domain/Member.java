package com.flab.dduikka.domain.member.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flab.dduikka.common.util.CustomValidator;
import com.flab.dduikka.domain.Auditable;

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
		// validate();
	}

	public boolean isJoined() {
		return memberStatus.equals(MemberStatus.JOIN);
	}

	private void validate() {
		validateEmail();
		validatePassword();
		validateMemberStatus();
		validateJoinDate();
		validateCreatedAt();
	}

	private void validateEmail() {
		String fieldValue = "email";
		CustomValidator.notBlank(email, fieldValue);
	}

	private void validatePassword() {
		String fieldValue = "password";
		CustomValidator.notBlank(password, fieldValue);
	}

	private void validateMemberStatus() {
		String fieldValue = "memberStatus";
		CustomValidator.notNull(memberStatus, fieldValue);
	}

	private void validateJoinDate() {
		String fieldValue = "joinDate";
		CustomValidator.notNull(joinDate, fieldValue);
		CustomValidator.pastOrPresent(joinDate, fieldValue);
	}

	private void validateCreatedAt() {
		String fieldValue = "createdAt";
		CustomValidator.notNull(getCreatedAt(), fieldValue);
	}

	private void validateLeaveDate() {
		String fieldValue = "leaveDate";
		CustomValidator.pastOrPresent(joinDate, fieldValue);
	}

}
