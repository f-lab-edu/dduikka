package com.flab.dduikka.domain.vote.repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.flab.dduikka.domain.vote.domain.Vote;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcVoteRepository implements VoteRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Vote createVote(final Vote vote) {

		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());
		SqlParameterSource param = new BeanPropertySqlParameterSource(vote);

		jdbcInsert
			.withTableName("vote")
			.usingGeneratedKeyColumns("vote_id");
		Long key = jdbcInsert.executeAndReturnKey(param).longValue();

		return Vote.builder().
			voteId(key).
			voteDate(vote.getVoteDate()).
			createdAt(vote.getCreatedAt()).
			build();
	}

	@Override
	public Optional<Vote> findByDate(LocalDate voteDate) {
		try {
			String sql = "select vote_id, vote_date, created_at from vote where vote_date=:voteDate";
			Map<String, Object> param = Map.of("voteDate", voteDate);
			Vote vote = jdbcTemplate.queryForObject(sql, param, voteRowMapper());
			assert vote != null;
			return Optional.of(vote);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	private RowMapper<Vote> voteRowMapper() {
		return (rs, rowNum) ->
			Vote.builder().
				voteId(rs.getLong("vote_id")).
				voteDate(rs.getDate("vote_date").toLocalDate()).
				createdAt(rs.getTimestamp("created_at").toLocalDateTime()).
				build();
	}
}
