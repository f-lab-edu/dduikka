package com.flab.dduikka.common.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseCleaner {

	private final List<String> tables = new ArrayList<>();

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void findDatabaseTableNames() {
		List<TableInfo> tableInfos = jdbcTemplate.query("SHOW TABLES", rowMapper());
		for (TableInfo tableInfo : tableInfos) {
			tables.add(tableInfo.getTableName());
		}
	}

	private void truncate() {
		CustomPreparedStatementCallback statementCallback = new CustomPreparedStatementCallback();
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = :flag", Map.of("flag", 0), statementCallback);
		for (String tableName : tables) {
			jdbcTemplate.execute(String.format("TRUNCATE TABLE %s", tableName), statementCallback);
		}
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = :flag", Map.of("flag", 1), statementCallback);
	}

	@Transactional
	public void clear() {
		truncate();
	}

	private RowMapper<TableInfo> rowMapper() {
		return (rs, rowNum) -> new TableInfo(rs.getString("Tables_in_test"));
	}

	class CustomPreparedStatementCallback implements PreparedStatementCallback<Boolean> {
		@Override
		public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			return ps.execute();
		}
	}

	class TableInfo {
		private String tableName;

		public TableInfo(String tableName) {
			this.tableName = tableName;
		}

		public String getTableName() {
			return tableName;
		}
	}
}
