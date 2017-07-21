package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.module.Module;
import org.centauri.cloud.rest.to.network.UpdateTO;
import org.centauri.cloud.rest.to.network.VersionTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/cloud", description = "administrative things")
@Path("/cloud")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CloudResource {

	@GET
	@Path("/version")
	@ApiOperation(value = "version of cloud", response = VersionTO.class)
	public Response getCloudVersion() {
		VersionTO versionTO = new VersionTO(Centauri.getInstance().getCloudVersion());
		return ok(versionTO);
	}

	@GET
	@Path("/modules")
	@ApiOperation(value = "gets a list of modules", responseContainer = "List")
	public Response getModules() {
		List<Module> moduleList = Centauri.getInstance().getModules();

		return ok();
	}

	@GET
	@Path("/updates")
	@ApiOperation(value = "gets updates", response = UpdateTO.class, responseContainer = "List")
	public Response getUpdates() {

		return ok();
	}

	@GET
	@Path("/logs")
	@ApiOperation(value = "feed of error messages")
	public Response getLogs() {

		return ok();
	}

	@GET
	@Path("/news")
	@ApiOperation(value = "news about the network")
	public Response getNews() {

		return ok();
	}

}
