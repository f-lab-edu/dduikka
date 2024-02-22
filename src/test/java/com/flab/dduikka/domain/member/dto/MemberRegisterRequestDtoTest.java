package com.flab.dduikka.domain.member.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

class MemberRegisterRequestDtoTest {

	@Test
	@DisplayName("회원 객체를 정상적으로 생성한다")
	void whenToEntityThenReturnsMember() {
		//given
		MemberRegisterRequestDto dto
			= new MemberRegisterRequestDto(
			"test@dduikka.net",
			"1234");

		//when
		Member createdMember = MemberRegisterRequestDto.to(dto);

		//then
		assertThat(createdMember.getEmail()).isEqualTo(dto.getEmail());
		assertThat(createdMember.getPassword()).isEqualTo(dto.getPassword());
		assertThat(createdMember.getMemberStatus()).isEqualTo(MemberStatus.JOIN);
		assertThat(createdMember.getJoinDate()).isEqualTo(LocalDate.now());
	}
}
