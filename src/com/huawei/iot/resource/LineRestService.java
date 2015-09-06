package com.huawei.iot.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.huawei.iot.service.BusLineService;
import com.huawei.iot.util.RestResult;

@Path("/line")
public class LineRestService {

	//@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON}) 
	@GET 
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public RestResult getTheLine(@QueryParam("LineNum") String lineNum, @QueryParam("PositionId") String positionId){
		RestResult result = new RestResult();
		result.setCode(RestResult.FAILED);
		result.setDescription("fail to post empty datas");
		if(null==lineNum || null == positionId
				){
			return result;
		}
	
		
		List resultList = BusLineService.getTheLine(lineNum, positionId);
		if(null == resultList || resultList.size() == 0){
			return result;
		}
		result.setCode(RestResult.SUCCESS);
		result.setData(resultList);
		result.setDescription("Success to update datas");
		return result;
	}
}
