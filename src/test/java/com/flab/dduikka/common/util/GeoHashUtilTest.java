package com.flab.dduikka.common.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GeoHashUtilTest {

	@Test
	@DisplayName("위경도를 통해 geohash 값을 조회한다.")
	void whenGetGeoHashString_thenReturnGeoHashString() {
		// given
		String latitude = "55.1";
		String longitude = "127.32";

		// when
		String geoHashString = GeoHashUtil.getGeoHashString(latitude, longitude);
		log.info("geoHashString = {}", GeoHashUtil.getGeoHashString(latitude, longitude));

		// then
		assertThat(geoHashString).isNotNull();
	}
}
