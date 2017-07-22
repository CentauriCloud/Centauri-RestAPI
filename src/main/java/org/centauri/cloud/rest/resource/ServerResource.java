package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.rest.auth.role.Role;
import org.centauri.cloud.rest.auth.role.SERVER;
import org.centauri.cloud.rest.to.server.ActionTO;
import org.centauri.cloud.rest.to.server.ServerInformationTO;
import org.centauri.cloud.rest.to.server.ServerTO;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/servers", description = "Doing things with servers. Servers are running instances of a template", authorizations = @Authorization("Bearer"))
@Path("/servers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {

	@GET
	@Path("/running")
	@ApiOperation(value = "gets a list of all running servers", response = ServerTO.class, responseContainer = "List")
	@RolesAllowed({Role.ADMIN, Role.MODERATOR, SERVER.ALL})
	public Response getRunningServers() {
		List<ServerTO> servers = Centauri.getInstance().getServers()
				.stream()
				.map(server -> new ServerTO(server.getPrefix(), null))
				.collect(Collectors.toList());
		return ok(servers);
	}

	@POST
	@Path("/new")
	@RolesAllowed({Role.ADMIN, SERVER.START})
	public Response spawnNewServer() {
		//TODO versteh ich net
		return null;
	}

	@GET
	@Path("/{id}")
	@ApiOperation(value = "gets some information about a single server", response = ServerInformationTO.class)
	@RolesAllowed({Role.ADMIN, Role.MODERATOR, SERVER.INFO})
	public Response getInformation(@PathParam("id") String serverId) {
		Server server = Centauri.getInstance().getServer(serverId);
		ServerInformationTO informationTO = new ServerInformationTO(server.getId() + "", server.getHost(), -1, server.getPing(), null, server.getPlayers());
		return ok(informationTO);
	}

	@POST
	@Path("/{id}")
	@ApiOperation(value = "executes a given action")
	@RolesAllowed({Role.ADMIN, SERVER.ACTIONS})
	public Response executeAction(@PathParam("id") String serverId, @ApiParam(value = "the action which should be triggered", required = true) ActionTO actionTO) {
		Centauri.getInstance().getServer(serverId);//do sumthin
		return ok();
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "stops a given server")
	@RolesAllowed({Role.ADMIN, SERVER.STOP})
	public Response stopServer(@PathParam("id") String serverId) {
		Server server = Centauri.getInstance().getServer(serverId);
		server.kill();
		return ok();
	}


}
