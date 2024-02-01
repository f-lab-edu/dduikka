package com.flab.dduikka.domain.vote.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.flab.dduikka.domain.vote.domain.VoteRecord;
import com.flab.dduikka.domain.vote.domain.VoteType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcVoteRecordRepository implements VoteRecordRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public VoteRecord createVoteRecord(VoteRecord voteRecord) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());
		SqlParameterSource param = new BeanPropertySqlParameterSource(voteRecord);

		jdbcInsert
			.withTableName("VOTE_RECORD")
			.usingGeneratedKeyColumns("VOTE_RECORD_ID");
		Long key = jdbcInsert.executeAndReturnKey(param).longValue();

		return VoteRecord.builder()
			.voteRecordId(key)
			.voteId(voteRecord.getVoteId())
			.userId(voteRecord.getUserId())
			.voteType(voteRecord.getVoteType())
			.isCanceled(voteRecord.getIsCanceled())
			.createdAt(voteRecord.getCreatedAt())
			.build();
	}

	@Override
	public Optional<VoteRecord> findByUserAndVoteAndIsCanceled(long userId, long voteId) {
		try {
			String sql = "select vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at from vote_record where vote_id = :voteId and user_id = :userId and is_canceled = 0 ";
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("voteId", voteId)
				.addValue("userId", userId);

			VoteRecord createdVoteRecord = jdbcTemplate.queryForObject(sql, param, voteRecordMapper());
			assert createdVoteRecord != null; // 뭘까?
			return Optional.of(createdVoteRecord);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<VoteRecord> findAllByVoteId(long voteId) {
		String sql = "select vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at from vote_record where vote_id = :voteId and is_canceled = 0 ";
		Map<String, Object> param = Map.of("voteId", voteId);
		return jdbcTemplate.query(sql, param, voteRecordMapper());
	}

	@Override
	public Optional<VoteRecord> findById(long voteRecordId) {
		try {
			String sql = "select vote_record_id, vote_id, user_id, vote_type, is_canceled, created_at from vote_record where vote_record_id = :voteRecordId and is_canceled = 0";
			Map<String, Object> param = Map.of("voteRecordId", voteRecordId);
			VoteRecord foundVoteRecord = jdbcTemplate.queryForObject(sql, param, voteRecordMapper());
			assert foundVoteRecord != null;
			return Optional.of(foundVoteRecord);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void cancelVoteRecord(VoteRecord foundVoteRecord) {
		String sql = "update vote_record set is_canceled = :isCanceled where vote_record_id = :voteRecordId ";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("isCanceled", foundVoteRecord.getIsCanceled())
			.addValue("voteRecordId", foundVoteRecord.getVoteRecordId());

		jdbcTemplate.update(sql, param);
	}

	private RowMapper<VoteRecord> voteRecordMapper() {
		return ((rs, rowNum) -> {
			return VoteRecord.builder()
				.voteRecordId(rs.getLong("vote_record_id"))
				.voteId(rs.getLong("vote_id"))
				.userId(rs.getLong("user_id"))
				.voteType(VoteType.valueOf(rs.getString("vote_type")))
				.isCanceled(rs.getBoolean("is_canceled"))
				.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
				.build();
		});
	}
}
