package com.huawei.iot.resource;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.huawei.iot.db.DBConstants;
import com.huawei.iot.db.DBOperator;
import com.huawei.iot.db.DIConstants;
import com.huawei.iot.service.BusLineService;
import com.huawei.iot.util.RestResult;

@Path("/message")
public class MessageService {

	@GET
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public RestResult setMessage(@QueryParam("content") String content) {
		RestResult result = new RestResult();
		result.setCode(RestResult.FAILED);
		result.setDescription("fail to post empty datas");
		
		if(null == content){
			result.setDescription("No message to input");
			return result;
		}
		DBOperator.putMessage(DBConstants.TBL_MESSAGE, content);
		result.setCode(RestResult.SUCCESS);
		result.setDescription("Success to update message");
		return result;
	}
	
	@GET
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	@Path("/{id}")
	public RestResult getMessage(@PathParam("id") String id) {
		RestResult result = new RestResult();
		result.setCode(RestResult.FAILED);
		result.setDescription("fail to post empty datas");
		
		if(null == id){
			result.setDescription("Please input the message id");
			return result;
		}
		List resultList = DBOperator.getMessages(DBConstants.TBL_MESSAGE, id);
		result.setData(resultList);
		result.setCode(RestResult.SUCCESS);
		result.setDescription("Success to update message");
		return result;
	}
}
