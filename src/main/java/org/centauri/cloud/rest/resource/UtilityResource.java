package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.centauri.cloud.rest.auth.role.Role;
import org.centauri.cloud.rest.auth.role.UTILITY;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/utility", description = "different things", authorizations = @Authorization("Bearer"))
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UtilityResource {

	@GET
	@Path("/files/list")
	@ApiOperation(value = "list files of directory")
	@RolesAllowed({Role.ADMIN, UTILITY.LIST_FILES})
	public Response getFiles(@QueryParam("path") String path) {

		return ok();
	}

	@DELETE
	@Path("/files/list")
	@ApiOperation(value = "delete a file or directory")
	@RolesAllowed({Role.ADMIN, UTILITY.DELETE_FILE})
	public Response deleteFiles(@QueryParam("path") String path) {

		return ok();
	}

	@POST
	@Path("/files/list")
	@ApiOperation(value = "upload a file")
	@RolesAllowed({Role.ADMIN, UTILITY.UPLOAD_FILE})
	public Response uploadFile(@QueryParam("path") String path) {

		return ok();
	}
}
