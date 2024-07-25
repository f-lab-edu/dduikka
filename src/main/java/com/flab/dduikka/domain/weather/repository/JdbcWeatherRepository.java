package com.flab.dduikka.domain.weather.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.flab.dduikka.domain.location.domain.Location;
import com.flab.dduikka.domain.weather.domain.Weather;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcWeatherRepository implements WeatherRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Optional<Weather> findWeatherById(String weatherId) {
		try {
			String sql = "select * from weather where weather_id =:weatherId";
			MapSqlParameterSource param = new MapSqlParameterSource()
				.addValue("weatherId", weatherId);

			Weather weather = jdbcTemplate.queryForObject(sql, param, weatherRowMapper());
			assert weather != null;
			return Optional.of(weather);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void addWeather(Weather weather) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate());
		Map<String, Object> param = new HashMap<>();
		param.put("weather_id", weather.getWeatherId());
		param.put("forecast_datetime", weather.getForecastDateTime());
		param.put("latitude", weather.getLocation().getLatitude());
		param.put("longitude", weather.getLocation().getLatitude());
		param.put("temperature", weather.getTemperature());
		param.put("relative_humidity", weather.getRelativeHumidity());
		param.put("rainfall", weather.getRainfall());
		param.put("snowfall", weather.getSnowfall());
		param.put("created_at", weather.getCreatedAt());
		jdbcInsert.withTableName("WEATHER");
		jdbcInsert.execute(param);
	}

	private RowMapper<Weather> weatherRowMapper() {
		return (rs, rowNum) ->
			Weather.builder()
				.weatherId(rs.getString("weather_id"))
				.forecastDateTime(rs.getTimestamp("forecast_datetime").toLocalDateTime())
				.location(new Location(rs.getString("latitude"), rs.getString("longitude")))
				.temperature(rs.getDouble("temperature"))
				.relativeHumidity(rs.getInt("relative_humidity"))
				.rainfall(rs.getDouble("rainfall"))
				.snowfall(rs.getDouble("snowfall"))
				.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
				.build();
	}
}
