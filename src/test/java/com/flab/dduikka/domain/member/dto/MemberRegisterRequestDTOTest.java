package com.flab.dduikka.domain.member.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

class MemberRegisterRequestDTOTest {

	@Test
	@DisplayName("회원 객체를 정상적으로 생성한다")
	void whenToEntityThenReturnsMember() {
		//given
		MemberRegisterRequestDTO dto
			= new MemberRegisterRequestDTO(
			"test@dduikka.net",
			"1234");
		String encodedPassword = "encodedPassword";

		//when
		Member createdMember = MemberRegisterRequestDTO.to(dto, encodedPassword);

		//then
		assertThat(createdMember.getEmail()).isEqualTo(dto.getEmail());
		assertThat(createdMember.getPassword()).isNotEqualTo(dto.getPassword());
		assertThat(createdMember.getMemberStatus()).isEqualTo(MemberStatus.JOIN);
		assertThat(createdMember.getJoinDate()).isEqualTo(LocalDate.now());
	}
}
