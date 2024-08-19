package com.flab.dduikka.common.util;

import ch.hsr.geohash.GeoHash;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeoHashUtil {

	public String getGeoHashString(String latitude, String longitude) {
		GeoHash geoHash =
			GeoHash.withCharacterPrecision(Double.parseDouble(latitude), Double.parseDouble(longitude), 7);
		return geoHash.toBase32();
	}

	public String getGeoHashString(String latitude, String longitude, int lengthOfHash) {
		GeoHash geoHash =
			GeoHash.withCharacterPrecision(Double.parseDouble(latitude), Double.parseDouble(longitude), lengthOfHash);
		return geoHash.toBase32();
	}
}
