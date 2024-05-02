package com.flab.dduikka.domain.location.domain;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {
	private String city;
	private String district;
	private String latitude;
	private String longitude;
	private String cityCode;

	public Location(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitudeAndLongitude() {
		return String.format("%s,%s", this.latitude, this.longitude);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Location location = (Location)o;
		return Objects.equals(city, location.city) && Objects.equals(district, location.district)
			&& Objects.equals(latitude, location.latitude) && Objects.equals(longitude,
			location.longitude) && Objects.equals(cityCode, location.cityCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, district, latitude, longitude, cityCode);
	}
}
