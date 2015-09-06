package com.huawei.iot.model;

import java.util.List;

public class BusLine {

	private String lineNum;
	private String startTime;
	private String endTime;
	private String price;
	private String isFavor;
	private String favorStationId;
	private List<Station> stations;

	public String getLineNum() {
		return lineNum;
	}

	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIsFavor() {
		return isFavor;
	}

	public void setIsFavor(String isFavor) {
		this.isFavor = isFavor;
	}

	public String getFavorStationId() {
		return favorStationId;
	}

	public void setFavorStationId(String favorStationId) {
		this.favorStationId = favorStationId;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	@Override
	public String toString() {
		return "BusLine [lineNum=" + lineNum + ", startTime=" + startTime + ", endTime=" + endTime + ", price=" + price
				+ ", isFavor=" + isFavor + ", favorStationId=" + favorStationId + ", stations=" + stations + "]";
	}
}
