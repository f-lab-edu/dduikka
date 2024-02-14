package com.flab.dduikka.domain.member.dto;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.flab.dduikka.domain.member.domain.Member;

class MemberResponseDtoTest {

	@Test
	void 응답_객체를_정상적으로_생성한다() {
		//given
		Member member = Member.builder()
			.memberId(1L)
			.email("dduikka@test.com")
			.joinDate(LocalDate.now())
			.build();

		//when
		MemberResponseDto response = MemberResponseDto.from(member);

		//then
		assertThat(member.getMemberId()).isEqualTo(response.getMemberId());
		assertThat(member.getEmail()).isEqualTo(response.getEmail());
		assertThat(member.getJoinDate()).isEqualTo(response.getJoinDate());
	}

}
