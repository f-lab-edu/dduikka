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
	public Optional<Member> findById(final long memberId) {
		try {
			String sql = "select member_id, email, password, member_status, join_date, created_at from member where member_id =:memberId";
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("memberId", memberId);
			Member foundUser = jdbcTemplate.queryForObject(sql, param, memberRecordMapper());
			assert foundUser != null;
			return Optional.of(foundUser);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private RowMapper<Member> memberRecordMapper() {
		return (rs, rowNum) ->
			Member.builder()
				.memberId(rs.getLong("member_id"))
				.email(rs.getString("email"))
				.password(rs.getString("password"))
				.memberStatus(MemberStatus.valueOf(rs.getString("member_status")))
				.joinDate(rs.getDate("join_date").toLocalDate())
				.createAt(rs.getTimestamp("created_at").toLocalDateTime())
				.build();
	}
}
