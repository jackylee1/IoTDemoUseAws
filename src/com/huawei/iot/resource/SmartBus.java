package com.huawei.iot.resource;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.huawei.iot.db.DBConstants;
import com.huawei.iot.db.DBOperator;
import com.huawei.iot.util.RestResult;

@Path("/device")
public class SmartBus {

	@POST
	@Produces("application/json;charset=UTF-8") 
	@Consumes("application/json;charset=UTF-8")
	public RestResult sayHelloToUTF8(@QueryParam("DeviceID") String deviceID,
			@QueryParam("PositionId") String positionId) {
		RestResult result = new RestResult();
		result.setCode(RestResult.FAILED);
		result.setDescription("fail to post empty datas");
		//post data to db
		Map<String, String> dbMap = new HashMap<String, String>();
		dbMap.put(DBConstants.DEVICE_ID, deviceID);
		dbMap.put(DBConstants.POSITION_ID, positionId);
		PutItemOutcome pio = DBOperator.updateItem(DBConstants.TBL_DEVICE_INSTANCE, dbMap);
		result.setCode(RestResult.SUCCESS);
		result.setData(pio);
		result.setDescription("Success to update datas");
		return result;
	}
}
