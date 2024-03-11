package com.flab.dduikka.domain.livechat.repository;

import java.util.List;

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
			.isDeleted(liveChat.getIsDeleted())
			.createdAt(liveChat.getCreatedAt())
			.build();
	}

	@Override
	public List<LiveChat> findAllLiveChat(long lastMessageId) {
		String sql = "select * from live_chat where live_chat_id < :lastMessageId order by live_chat_id desc limit :limit";
		MapSqlParameterSource param = new MapSqlParameterSource()
			.addValue("lastMessageId", lastMessageId)
			.addValue("limit", LIMIT);
		return jdbcTemplate.query(sql, param, liveChatMapper());
	}

	private RowMapper<LiveChat> liveChatMapper() {
		return (rs, rowNum) ->
			LiveChat.builder()
				.liveChatId(rs.getLong("live_chat_id"))
				.memberId(rs.getLong("member_id"))
				.message(rs.getString("message"))
				.isDeleted(rs.getBoolean("is_deleted"))
				.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
				.build();
	}
}
