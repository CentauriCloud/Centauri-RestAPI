package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/utility", description = "different things")
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UtilityResource {

	@GET
	@Path("/files/list")
	@ApiOperation(value = "list files of directory")
	public Response getFiles(@QueryParam("path") String path) {

		return ok();
	}

	@DELETE
	@Path("/files/list")
	@ApiOperation(value = "delete a file or directory")
	public Response deleteFiles(@QueryParam("path") String path) {

		return ok();
	}

	@POST
	@Path("/files/list")
	@ApiOperation(value = "upload a file")
	public Response uploadFile(@QueryParam("path") String path) {

		return ok();
	}
}
