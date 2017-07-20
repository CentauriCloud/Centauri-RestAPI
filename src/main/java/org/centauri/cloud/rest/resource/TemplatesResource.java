package org.centauri.cloud.rest.resource;

import io.swagger.annotations.Api;
import org.centauri.cloud.cloud.api.Centauri;
import org.centauri.cloud.cloud.template.Template;
import org.centauri.cloud.rest.annotations.Nothing;
import org.centauri.cloud.rest.annotations.Returns;
import org.centauri.cloud.rest.annotations.Takes;
import org.centauri.cloud.rest.to.template.TemplateInformationTO;
import org.centauri.cloud.rest.to.template.TemplateTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
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
	@Takes(Nothing.class)
	@Returns(TemplateTO.class)
	public Response getInstalledTemplates() {
		List<TemplateTO> templates = Centauri.getInstance().getTemplates()
				.stream()
				.map(template -> new TemplateTO(template.getName()))
				.collect(Collectors.toList());
		return ok(templates);
	}

	@GET
	@Path("/{id}")
	@Takes(Nothing.class)
	@Returns(TemplateInformationTO.class)
	public Response getInformationFromTemplate(@PathParam("id") String templateName) {
		Template template = Centauri.getInstance().getTemplate(templateName);
		TemplateInformationTO infoTO = new TemplateInformationTO(template.getName(), template.getName(), template.getMaxPlayers(), null, -1, -1);
		return ok(infoTO);
	}

	@POST
	@Path("/{id}")
	@Takes(Nothing.class)
	@Returns(Nothing.class)
	public Response uploadConfiguration(@PathParam("id") String templateName, List<String> lines) {
		Centauri.getInstance().setConfigFromTemplate(templateName, lines);
		return ok();
	}

	@GET
	@Path("/{id}/files")
	@Takes(Nothing.class)
	@Returns(File.class)
	public Response getFiles(@PathParam("id") String templateId) {
		Template template = Centauri.getInstance().getTemplate(templateId);
		return ok(template.getDir().listFiles());
	}


}
