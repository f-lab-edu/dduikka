package com.flab.dduikka.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRegisterRequestDto {

	@Email(message = "email 형식이 알맞지 않습니다.")
	@NotBlank(message = "email은 비어있거나 null일 수 없습니다.")
	private String email;
	@Size(min = 10, message = "비밀번호는 최소 10자리 이상이어야 합니다")
	@NotBlank(message = "비밀번호는 비어있거나 null일 수 없습니다.")
	private String password;
	@NotNull(message = "memberStatus는 null일 수 없습니다.")
	private MemberStatus memberStatus;
	@NotNull(message = "가입일자는 null일 수 없습니다.")
	@PastOrPresent(message = "가입일자는 미래날짜일 수 없습니다.")
	private LocalDate joinDate;

	public static Member to(MemberRegisterRequestDto request) {
		return Member.builder()
			.email(request.email)
			.password(request.password)
			.memberStatus(request.memberStatus)
			.joinDate(request.joinDate)
			.createAt(LocalDateTime.now())
			.build();
	}
}
