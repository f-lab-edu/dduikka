package com.flab.dduikka.domain.member.repository;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flab.dduikka.domain.member.domain.Member;
import com.flab.dduikka.domain.member.domain.MemberStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcMemberRepository implements MemberRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Optional<Member> findByIdAndMemberStatus(final long memberId, final MemberStatus memberStatus) {
		try {
			String sql = "select member_id, email, member_status, join_date, created_at from member where member_id =:memberId and member_status=:memberStatus";
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("memberId", memberId)
				.addValue("memberStatus", memberStatus.name());
			Member foundUser = jdbcTemplate.queryForObject(sql, param, MemberRecordMapper());
			assert foundUser != null;
			return Optional.of(foundUser);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private RowMapper<Member> MemberRecordMapper() {
		return (rs, rowNum) ->
			Member.builder()
				.memberId(rs.getLong("member_id"))
				.email(rs.getString("email"))
				.memberStatus(MemberStatus.valueOf(rs.getString("member_status")))
				.joinDate(rs.getDate("join_date").toLocalDate())
				.createAt(rs.getTimestamp("created_at").toLocalDateTime())
				.build();
	}
}
