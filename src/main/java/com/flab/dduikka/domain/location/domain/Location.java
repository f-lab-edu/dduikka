package com.flab.dduikka.domain.location.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Location {
	private String city; //시
	private String district; //도
	private String latitude; //위도
	private String longitude; //경도
	private String cityCode; //accuweatherKey

	public Location(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitudeAndLongitude() {
		return String.format("%s,%s", this.latitude, this.longitude);
	}
}
