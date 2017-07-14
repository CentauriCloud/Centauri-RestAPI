package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import org.centauri.cloud.rest.util.MapUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("/test")
@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {

	@GET
	@Path("geilerscheiss")
	public Response pleaseGiveMeATestResponse() {
		return Response.status(200).entity(MapUtil.from("STATUS", "SUCCESS")).build();
	}


}
