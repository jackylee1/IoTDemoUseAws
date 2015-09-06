package com.huawei.iot.db;

public class DIConstants {

	//表名
	public static final String TBL_DEVICE_TYPE = "DeviceType";
	public static final String TBL_DEVICE_INSTANCE = "DeviceInstance";
	public static final String TBL_DATA_STREAM = "DataStream";
	public static final String TBL_DTID = "DTID";
	public static final String TBL_DIID = "DIID";
	
	//查询条件
	public static final String CONDITION_KEY = "conditionKey";
	public static final String CONDITION_VALUE = "conditionValue";
	
	//操作flag
	public static final int operate_get_items = 0;
	public static final int operate_add_item = 1;
	public static final int operate_del_item = 2;
	public static final int operate_mdf_item = 3;
	
	//数据 DeviceType
	public static final String DEVICE_TYPE_ID = "DeviceTypeID";
	public static final String MASTER_KEY = "MasterKey";
	public static final String CREATE_TIME = "CreateTime";
	public static final String DATA_STREAM = "DataStream";
	public static final String DESCRIPTION = "Description";
	public static final String DEVICE_TYPE_NAME = "DeviceTypeName";
	public static final String MANAGEMENT_PROTOCOL = "ManagementProtocol";
	public static final String SERVICE_PROTOCOL = "ServiceProtocol";
	public static final String PLATFORM = "Platform";
	public static final String STATUS = "Status";
	//to 设备实例
	public static final String NO_TYPE = "No_Type";
	public static final String ALREADY_EXIST = "Already_Exist";
	//操作字段
	public static final String OPERATION = "Operation";
	public static final String OPERATION_RESTART = "Restart";
	public static final String OPERATION_POWEROFF = "Poweroff";
	public static final String OPERATION_FOTA = "Fota";
	public static final String OPERATION_DIAGNOSIS = "Diagnosis";
	public static final String OPERATION_CONFIG = "Configuration";
	public static final String[] OPERATIONS = {
			OPERATION_RESTART,
			OPERATION_POWEROFF,
			OPERATION_FOTA,
			OPERATION_DIAGNOSIS,
			OPERATION_CONFIG
	};
	
	
	public static final String STATUS_ENABLE = "enable";
	public static final String STATUS_DISABLE = "disable";
	
	//DATA_STREAM
	public static final String DATA_STREAMS = "DataStreams";
	public static final String DATA_STREAM_DISPLAY_NAME = "DataStreamDisplayName";
	public static final String DATA_STREAM_NAME = "DataStreamName";
	public static final String DATA_STREAM_TYPE = "DataStreamType";
	public static final String DATA_STREAM_UNIT = "DataStreamUnit";
	public static final String DATA_STREAM_ID = "DataStreamID";
	public static final String DATA_STREAM_VALUE = "Value";
	public static final String DATA_CREATE_TIME = "CreateTime";
	
	public static final String LONGITUDE = "Longitude";//经度
	public static final String LATITUDE = "Latitude";//纬度
	public static final String ALTITUDE = "Altitude";//海拔
	public static final String SPEED = "Speed";//车速
	
	//数据DTID
	public static final String DTID_ID = "ID";
	public static final String DTID_MASTER_KEY = "masterKey";
	public static final String DIID_ID = "ID";
	public static final String DIID_CURRENT_TIME = "CurrentTime";
	
	//数据DeviceInstance
	public static final String DEVICE_ID = "DeviceID";
	public static final String DEVICE_NAME = "DeviceName";
	public static final String SIM_CARD_ID = "SimCardID";
    public static final String BUSLINE_NUM = "BusLineNum";
    public static final String REMAIN_TIME = "RemainTime";
    public static final String POSITION_ID = "PositionId";
	public static final String FIRST_CONNECT_TIME = "FirstConnectTime";
	public static final String LAST_CONNECT_TIME = "LastConnectTime";
	
	public static final String DEVICE_STATUS = "Status";
	public static final String DEVICE_STATUS_ONLINE = "online";
	public static final String DEVICE_STATUS_OFFLINE = "offline";
	public static final String DEVICE_ACTION = "action";
	public static final String DEVICE_STATUS_DISABLE = "disable";
	public static final String DEVICE_STATUS_ENABLE = "enable";
	
	//Qty
	public static final String QTY_INSTANCE_ENABLE = "DevicesEnabled"; 
	public static final String QTY_INSTANCE = "Devices"; 
	public static final String QTY_INSTANCE_ONLINE = "DevicesOnline"; 
	public static final String QTY_INSTANCE_OFFLINE = "DevicesOffline"; 
}
