package com.flab.dduikka.domain.livechat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.flab.dduikka.domain.livechat.domain.LiveChat;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JDBCLiveChatRepository implements LiveChatRepository {
	private static final long LIMIT = 5;
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Optional<LiveChat> findById(long liveChatId) {
		try {
			String sql = "select * from live_chat where live_chat_id =:liveChatId";
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("liveChatId", liveChatId);
			LiveChat foundLiveChat = jdbcTemplate.queryForObject(sql, param, liveChatMapper());
			assert foundLiveChat != null;
			return Optional.of(foundLiveChat);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public LiveChat addLiveChat(LiveChat liveChat) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());
		SqlParameterSource param = new BeanPropertySqlParameterSource(liveChat);

		jdbcInsert
			.withTableName("LIVE_CHAT")
			.usingGeneratedKeyColumns("LIVE_CHAT_ID");
		Long key = jdbcInsert.executeAndReturnKey(param).longValue();

		return LiveChat.builder()
			.liveChatId(key)
			.memberId(liveChat.getMemberId())
			.message(liveChat.getMessage())
			.deletedFlag(liveChat.isDeletedFlag())
			.createdAt(liveChat.getCreatedAt())
			.build();
	}

	@Override
	public List<LiveChat> findAllLiveChat(long lastMessageId) {
		String sql = "select * from live_chat where live_chat_id < :lastMessageId and deleted_flag = 0 order by live_chat_id desc limit :limit";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("lastMessageId", lastMessageId)
			.addValue("limit", LIMIT);
		return jdbcTemplate.query(sql, param, liveChatMapper());
	}

	@Override
	public void deleteLiveChat(LiveChat liveChat) {
		String sql = "update live_chat set deleted_flag = :deletedFlag where live_chat_id = :liveChatId";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("deletedFlag", liveChat.isDeletedFlag())
			.addValue("liveChatId", liveChat.getLiveChatId());
		jdbcTemplate.update(sql, param);
	}

	private RowMapper<LiveChat> liveChatMapper() {
		return (rs, rowNum) ->
			LiveChat.builder()
				.liveChatId(rs.getLong("live_chat_id"))
				.memberId(rs.getLong("member_id"))
				.message(rs.getString("message"))
				.deletedFlag(rs.getBoolean("deleted_flag"))
				.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
				.build();
	}
}
