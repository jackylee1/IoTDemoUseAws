package com.huawei.iot.util;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.huawei.iot.db.DIConstants;

public class TransferDI2Result {
public static Map<String, Object> converFromDeviceInstance(Map<String, AttributeValue> map) {
		
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put(DIConstants.DEVICE_ID, map.get(DIConstants.DEVICE_ID).getS());
		
		mapResult.put(DIConstants.DEVICE_NAME, map.get(DIConstants.DEVICE_NAME).getS());
		mapResult.put(DIConstants.DEVICE_TYPE_ID, map.get(DIConstants.DEVICE_TYPE_ID).getS());
		mapResult.put(DIConstants.DEVICE_TYPE_NAME, map.get(DIConstants.DEVICE_TYPE_NAME).getS());
		mapResult.put(DIConstants.SIM_CARD_ID, map.get(DIConstants.SIM_CARD_ID).getS());
		mapResult.put(DIConstants.FIRST_CONNECT_TIME, map.get(DIConstants.FIRST_CONNECT_TIME).getS());
		mapResult.put(DIConstants.LAST_CONNECT_TIME, map.get(DIConstants.LAST_CONNECT_TIME).getS());
		mapResult.put(DIConstants.DEVICE_STATUS, map.get(DIConstants.DEVICE_STATUS).getS());
		mapResult.put(DIConstants.MASTER_KEY, map.get(DIConstants.MASTER_KEY).getS());
		if(map.containsKey(DIConstants.LATITUDE))
			mapResult.put(DIConstants.LATITUDE, map.get(DIConstants.LATITUDE).getS());
		if(map.containsKey(DIConstants.ALTITUDE))
			mapResult.put(DIConstants.ALTITUDE, map.get(DIConstants.ALTITUDE).getS());
		if(map.containsKey(DIConstants.LONGITUDE))
			mapResult.put(DIConstants.LONGITUDE, map.get(DIConstants.LONGITUDE).getS());
		if(map.containsKey(DIConstants.SPEED))
			mapResult.put(DIConstants.SPEED, map.get(DIConstants.SPEED).getS());
		if(map.containsKey(DIConstants.PLATFORM)){
			mapResult.put(DIConstants.PLATFORM, map.get(DIConstants.PLATFORM).getS());
		}
		if(map.containsKey(DIConstants.BUSLINE_NUM)){
			mapResult.put(DIConstants.BUSLINE_NUM, map.get(DIConstants.BUSLINE_NUM).getS());
		}
		if(map.containsKey(DIConstants.REMAIN_TIME)){
			mapResult.put(DIConstants.REMAIN_TIME, map.get(DIConstants.REMAIN_TIME).getS());
		}
		if(map.containsKey(DIConstants.POSITION_ID)){
			mapResult.put(DIConstants.POSITION_ID, map.get(DIConstants.POSITION_ID).getS());
		}
		
		//½âÎö²Ù×÷
		if(map.containsKey(DIConstants.OPERATION)){
			Map<String, Object> operMap = new HashMap<String, Object>();
			Map<String, AttributeValue> opMap = (Map<String, AttributeValue>) map.get(DIConstants.OPERATION).getM();
			operMap.put(DIConstants.OPERATION_RESTART, opMap.get(DIConstants.OPERATION_RESTART).getS());
			operMap.put(DIConstants.OPERATION_POWEROFF, opMap.get(DIConstants.OPERATION_POWEROFF).getS());
			operMap.put(DIConstants.OPERATION_FOTA, opMap.get(DIConstants.OPERATION_FOTA).getS());
			operMap.put(DIConstants.OPERATION_DIAGNOSIS, opMap.get(DIConstants.OPERATION_DIAGNOSIS).getS());
			operMap.put(DIConstants.OPERATION_CONFIG, opMap.get(DIConstants.OPERATION_CONFIG).getS());
			
			mapResult.put(DIConstants.OPERATION, operMap);
		}
		Map<String, Object> datastreams = new HashMap<String, Object>();

		if (map.containsKey(DIConstants.DATA_STREAMS)) {
			Map<String, AttributeValue> joMap = map.get(DIConstants.DATA_STREAMS).getM();
			for (int i = 1; i < joMap.size() + 1; i++) {
				Map<String, Object> datastream = new HashMap<String, Object>();
				AttributeValue dataStreamDisplayName = joMap.get(DIConstants.DATA_STREAM + (i + "")).getM().get(DIConstants.DATA_STREAM_DISPLAY_NAME);
				AttributeValue dataStreamName = joMap.get(DIConstants.DATA_STREAM + (i + "")).getM().get(DIConstants.DATA_STREAM_NAME);
				AttributeValue dataStreamType = joMap.get(DIConstants.DATA_STREAM + (i + "")).getM().get(DIConstants.DATA_STREAM_TYPE);
				AttributeValue dataStreamUnit = joMap.get(DIConstants.DATA_STREAM + (i + "")).getM().get(DIConstants.DATA_STREAM_UNIT);
				AttributeValue dataStreamId = joMap.get(DIConstants.DATA_STREAM + (i + "")).getM().get(DIConstants.DATA_STREAM_ID);
				
				datastream.put(DIConstants.DATA_STREAM_NAME, dataStreamName.getS());
				datastream.put(DIConstants.DATA_STREAM_DISPLAY_NAME, dataStreamDisplayName.getS());
				datastream.put(DIConstants.DATA_STREAM_TYPE, dataStreamType.getS());
				datastream.put(DIConstants.DATA_STREAM_UNIT, dataStreamUnit.getS());
				datastream.put(DIConstants.DATA_STREAM_ID, dataStreamId.getS());
				datastreams.put(DIConstants.DATA_STREAM + (i + ""), datastream);
			}


			mapResult.put(DIConstants.DATA_STREAMS, datastreams);
		}
		return mapResult;

	}
}
