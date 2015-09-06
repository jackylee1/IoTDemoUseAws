package com.huawei.iot.model;

public class Station {

	private String id;
	private String name;
	private String longitude;
	private String latitude;
	private String altitude;
	private String speed;
	private String remainTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", name=" + name + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", altitude=" + altitude + ", speed=" + speed + ", remainTime=" + remainTime + "]";
	}

}
