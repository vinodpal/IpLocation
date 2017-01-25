package com.iptrack.service;



import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.ipTrack.IpTrack;

/**
 * @author vinod<vinodpal458@gmail.com
 * 
 */

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Service {
	@GET
	@Path("/do/ipservice")
	@Produces("text/xml")
	public Response ipservice(@Context HttpServletRequest requestContext,/*@Context SecurityContext context*/
			@QueryParam("t") String token,
			@QueryParam("filter") String filter) {

		IpTrack ip = new IpTrack();
		String response = ip.getClientIpAddress(requestContext, token, filter);
		ResponseBuilder rBuild = Response.status(Response.Status.OK).header("Access-Control-Allow-Origin", "*");
		return rBuild.type(MediaType.APPLICATION_JSON).entity(response).build();
	}
}
