package com.huawei.iot.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.huawei.iot.db.DBOperator;

@Path("/hello")
public class HelloResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello World!";
	}

	@GET
	@Path("/{mTable}")
	@Produces("text/plain;charset=UTF-8")
	public String sayHelloToUTF8(@PathParam("mTable") String mTable) {
		return "table name: " + mTable;
	}
}
