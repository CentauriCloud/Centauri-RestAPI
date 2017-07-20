package org.centauri.cloud.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.centauri.cloud.rest.resource.*;

public class RestApplication extends Application<RestConfiguration> {

	public static void main(String[] args) throws Exception {
		new RestApplication().run(args);
	}


	@Override
	public void initialize(Bootstrap<RestConfiguration> bootstrap) {
		bootstrap.addBundle(new AssetsBundle("/swagger", "/swagger", "index.html"));

	}

	@Override
	public void run(RestConfiguration restConfiguration, Environment environment) throws Exception {
		environment.jersey().register(ApiListingResource.class);
		environment.jersey().register(SwaggerSerializers.class);
		environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

		BeanConfig config = new BeanConfig();
		config.setTitle("CentauriCloud RestAPI");
		config.setVersion("1.0.0");
		config.setResourcePackage("org.centauri.cloud.rest.resource");
		config.setScan(true);
		environment.jersey().register(CORSResponseFilter.class);

		environment.jersey().register(NetworkResource.class);
		environment.jersey().register(ServerResource.class);
		environment.jersey().register(TemplatesResource.class);
		environment.jersey().register(UserResource.class);
	}
}
