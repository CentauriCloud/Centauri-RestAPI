package org.centauri.cloud.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.centauri.cloud.rest.resource.CORSResponseFilter;
import org.centauri.cloud.rest.resource.TestResource;

public class RestApplicaiton extends Application<RestConfiguration> {

	public static void main(String[] args) throws Exception {
		new RestApplicaiton().run(args);
	}


	@Override
	public void initialize(Bootstrap<RestConfiguration> bootstrap) {
		bootstrap.addBundle(new SwaggerBundle<RestConfiguration>() {
			@Override
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(RestConfiguration configuration) {
				return configuration.getSwaggerBundleConfiguration();
			}
		});
	}

	@Override
	public void run(RestConfiguration restConfiguration, Environment environment) throws Exception {
		environment.jersey().register(TestResource.class);
		environment.jersey().register(CORSResponseFilter.class);
	}
}
