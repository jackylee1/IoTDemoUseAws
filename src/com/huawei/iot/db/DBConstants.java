package com.huawei.iot.db;

public class DBConstants {

	public static final String TBL_BUSLINE = "BusLine";
	public static final String TBL_STATION = "Station";
	public static final String TBL_DEVICE_TYPE = "DeviceType";
	public static final String TBL_DEVICE_INSTANCE = "DeviceInstance";
	
	public static final String HASH_KEY_NAME = "ID";
	
	//busline
	public static final String LINE_START_TIME = "StartTime";
	public static final String LINE_END_TIME = "EndTime";
	public static final String LINE_PRICE = "Price";
	public static final String LINE_STATUS = "Status";
	
	public static final String STATUS_ENABLE = "enable";
	
	//station
	public static final String STATION_ID = "StationID";
	public static final String STATION_NAME = "Name";
	public static final String STATION_LINE_ID = "LineID";
	public static final String STATION_LON = "Longitude";
	public static final String STATION_LAT = "Latitude";
	public static final String STATION_ALT = "Altitude";
	public static final String STATION_SPE = "Speed";
	public static final String STATION_REMAIN_TIME = "RemainTime";
	
	//device instance
	public static final String DEVICE_ID = "DeviceID";
    public static final String POSITION_ID = "PositionId";
    public static final String BUSLINE_NUM = "BusLineNum";
    public static final String REMAIN_TIME = "RemainTime";
    
    //bus datas to display
    public static final String DEVICE_NAME = "DeviceName";
    public static final String DEVICE_POSITION_ID = "PositionId";
    public static final String DEVICE_REMAIN_STOPS = "RemainStops";
    public static final String DEVICE_REMAIN_TIMES = "RemainTime";
}
