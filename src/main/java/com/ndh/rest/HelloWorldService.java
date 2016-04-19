package com.ndh.rest;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/hello")
public class HelloWorldService {
	
	private Logger log = LoggerFactory.getLogger(HelloWorldService.class);
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMsg(@PathParam("param") String msg) {
		
		String output = "{'Jersey say':'" + msg + "'}";
		
		log.debug("hello , 大家好");		
		
		return Response.ok(output).build();
 
	}
 
}