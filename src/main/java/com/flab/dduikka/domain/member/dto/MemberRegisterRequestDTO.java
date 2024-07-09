package com.flab.dduikka.domain.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRegisterRequestDTO {

	@Email(message = "email 형식이 알맞지 않습니다.")
	@NotBlank(message = "email은 비어있거나 null일 수 없습니다.")
	private String email;
	@Size(min = 10, message = "비밀번호는 최소 10자리 이상이어야 합니다")
	@NotBlank(message = "비밀번호는 비어있거나 null일 수 없습니다.")
	private String password;

	public static Member to(MemberRegisterRequestDTO request, String encodedPassword) {
		return Member.builder()
			.email(request.email)
			.password(encodedPassword)
			.memberStatus(MemberStatus.JOIN)
			.joinDate(LocalDate.now())
			.createAt(LocalDateTime.now())
			.build();
	}
}
