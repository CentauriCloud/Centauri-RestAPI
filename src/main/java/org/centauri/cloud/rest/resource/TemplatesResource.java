package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.template.Template;
import org.centauri.cloud.rest.to.template.TemplateInformationTO;
import org.centauri.cloud.rest.to.template.TemplateTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static org.centauri.cloud.rest.util.ResponseFactory.ok;

@Api(value = "/templates", description = "Things going on with templates")
@Path("/templates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TemplatesResource {

	@GET
	@Path("/installed")
	@ApiOperation(value = "gets a list of all templates available", response = TemplateTO.class, responseContainer = "List")
	public Response getInstalledTemplates() {
		List<TemplateTO> templates = Centauri.getInstance().getTemplates()
				.stream()
				.map(template -> new TemplateTO(template.getName()))
				.collect(Collectors.toList());
		return ok(templates);
	}

	@GET
	@Path("/{id}")
	@ApiOperation(value = "gets some information about a single template", response = TemplateInformationTO.class)
	public Response getInformationFromTemplate(@PathParam("id") String templateName) {
		Template template = Centauri.getInstance().getTemplate(templateName);
		TemplateInformationTO infoTO = new TemplateInformationTO(template.getName(), template.getName(), template.getMaxPlayers(), null, -1, -1);
		return ok(infoTO);
	}

	@POST
	@Path("/{id}")
	@ApiOperation(value = "uploads a new configuration for a given template")
	public Response uploadConfiguration(@PathParam("id") String templateName, @ApiParam(value = "Lines of the configuration", required = true) List<String> lines) {
		Centauri.getInstance().setConfigFromTemplate(templateName, lines);
		return ok();
	}

	@GET
	@Path("/{id}/files")
	@ApiOperation(value = "gets the files of a template")
	public Response getFiles(@PathParam("id") String templateId) {
		Template template = Centauri.getInstance().getTemplate(templateId);
		return ok(template.getDir().listFiles());
	}

	@POST
	@Path("/{id}/files")
	@ApiOperation(value = "Uploads a new file")
	public Response uploadFile(@PathParam("id") String templateId) {

		return ok();
	}

	@DELETE
	@Path("/{id}")
	@ApiOperation(value = "deletes a given template")
	public Response deleteTemplate(@PathParam("id") String templateId) {

		return ok();
	}

	@GET
	@Path("/{id}/pack")
	@ApiOperation(value = "gives you a zip of the whole template")
	public Response downloadZip(@PathParam("id") String templateId) {

		return ok();
	}

}
