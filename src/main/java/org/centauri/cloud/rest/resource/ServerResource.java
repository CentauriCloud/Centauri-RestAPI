package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.server.Server;
import org.centauri.cloud.rest.annotations.Nothing;
import org.centauri.cloud.rest.annotations.Returns;
import org.centauri.cloud.rest.annotations.Takes;
import org.centauri.cloud.rest.to.server.ActionTO;
import org.centauri.cloud.rest.to.server.ServerInformationTO;
import org.centauri.cloud.rest.to.server.ServerTO;
import org.centauri.cloud.rest.util.ResponseFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/servers", description = "Doing things with servers. Servers are running instances of a template")
@Path("/servers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServerResource {

	@GET
	@Path("/running")
	@Takes(Nothing.class)
	@Returns(ServerTO.class)
	public Response getRunningServers() {
		List<ServerTO> servers = Centauri.getInstance().getServers()
				.stream()
				.map(server -> new ServerTO(server.getPrefix(), null))
				.collect(Collectors.toList());
		return ok(servers);
	}

	@POST
	@Path("/new")
	public Response spawnNewServer() {
		//TODO versteh ich net
		return null;
	}

	@GET
	@Path("/{id}")
	@Takes(Nothing.class)
	@Returns(ServerInformationTO.class)
	public Response getInformation(@PathParam("id") String serverId) {
		Server server = Centauri.getInstance().getServer(serverId);
		ServerInformationTO informationTO = new ServerInformationTO(server.getId() + "", server.getHost(), -1, server.getPing(), null, server.getPlayers());
		return ok(informationTO);
	}

	@POST
	@Path("/{id}")
	@Takes(ActionTO.class)
	@Returns(Nothing.class)
	public Response executeAction(@PathParam("id") String serverId, ActionTO actionTO) {
		Centauri.getInstance().getServer(serverId);//do sumthin
		return ok();
	}

	@DELETE
	@Path("/{id}")
	@Takes(Nothing.class)
	@Returns(Nothing.class)
	public Response stopServer(@PathParam("id") String serverId) {
		Server server = Centauri.getInstance().getServer(serverId);
		server.kill();
		return ok();
	}


}
