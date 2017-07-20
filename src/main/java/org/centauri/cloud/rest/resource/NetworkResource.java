package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.server.BungeeServer;
import org.centauri.cloud.cloud.server.Daemon;
import org.centauri.cloud.cloud.server.SpigotServer;
import org.centauri.cloud.rest.annotations.Nothing;
import org.centauri.cloud.rest.annotations.Returns;
import org.centauri.cloud.rest.annotations.Takes;
import org.centauri.cloud.rest.to.network.HealthTO;
import org.centauri.cloud.rest.to.network.NodeTO;
import org.centauri.cloud.rest.to.network.PlayerMetricsTO;
import org.centauri.cloud.rest.util.ResponseFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/network", description = "Some specialized things about network, metrics, etc.")
@Path("/network")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NetworkResource {

	@GET
	@Path("/players")
	@Takes(Nothing.class)
	@Returns(PlayerMetricsTO.class)
	public Response getPlayerCount() {
		Centauri.getInstance();//.getAllPlayers();
		return ok(new PlayerMetricsTO());
	}

	@GET
	@Path("/nodes")
	@Takes(Nothing.class)
	@Returns(NodeTO.class)
	public Response getBungeeServers() {
		List<SpigotServer> spigotServers = Centauri.getInstance().getSpigotServers();
		List<BungeeServer> bungeeServers = Centauri.getInstance().getBungeeServers();
		List<Daemon> daemons = Centauri.getInstance().getDaemons();

		NodeTO nodeTO = new NodeTO(daemons.size(), bungeeServers.size(), spigotServers.size());
		return ok(nodeTO);
	}

	@GET
	@Path("/health")
	@Takes(Nothing.class)
	@Returns(HealthTO.class)
	public Response getHealthDataFromNetwork() {
		List<Daemon> daemons = Centauri.getInstance().getDaemons();
		//...
		return ok(new HealthTO());
	}

}