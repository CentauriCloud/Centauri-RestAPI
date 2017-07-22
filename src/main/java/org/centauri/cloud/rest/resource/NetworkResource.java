package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.rest.auth.role.NETWORK;
import org.centauri.cloud.rest.auth.role.Role;
import org.centauri.cloud.rest.to.network.HealthTO;
import org.centauri.cloud.rest.to.network.NodeTO;
import org.centauri.cloud.rest.to.network.PlayerMetricsTO;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/network", description = "Some specialized things about network, metrics, etc.", authorizations = @Authorization("Bearer"))
@Path("/network")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NetworkResource {

	@GET
	@Path("/players")
	@ApiOperation(value = "gets the player count", response = PlayerMetricsTO.class)
	@RolesAllowed({Role.ADMIN, Role.MODERATOR, NETWORK.PLAYERS})
	public Response getPlayerCount() {
		Centauri.getInstance();//.getAllPlayers();
		return ok(new PlayerMetricsTO());
	}

	@GET
	@Path("/nodes")
	@ApiOperation(value = "gets informations about nodes", response = NodeTO.class)
	@RolesAllowed({Role.ADMIN, Role.MODERATOR, NETWORK.NODES})
	public Response getBungeeServers() {
		List<SpigotServer> spigotServers = Centauri.getInstance().getSpigotServers();
		List<BungeeServer> bungeeServers = Centauri.getInstance().getBungeeServers();
		List<Daemon> daemons = Centauri.getInstance().getDaemons();

		NodeTO nodeTO = new NodeTO(daemons.size(), bungeeServers.size(), spigotServers.size());
		return ok(nodeTO);
	}

	@GET
	@Path("/health")
	@ApiOperation(value = "gets some health data about the whole network", response = HealthTO.class)
	@RolesAllowed({Role.ADMIN, NETWORK.HEALTH})
	public Response getHealthDataFromNetwork() {
		List<Daemon> daemons = Centauri.getInstance().getDaemons();
		//...
		return ok(new HealthTO());
	}

}
