package org.centauri.cloud.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.centauri.cloud.cloud.Cloud;
import org.centauri.cloud.rest.auth.jwt.JWTUtil;
import org.centauri.cloud.rest.auth.jwt.JwtAuthenticator;
import org.centauri.cloud.rest.auth.jwt.JwtAuthorizer;
import org.centauri.cloud.rest.auth.jwt.JwtUser;
import org.centauri.cloud.rest.resource.*;
import org.centauri.cloud.rest.resource.exceptions.DefaultExceptionMapper;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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
		Cloud.getLogger().info("run start");
		JWTUtil.init();

		environment.jersey().register(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<>()
				.setAuthenticator(new JwtAuthenticator())
				.setAuthorizer(new JwtAuthorizer())
				.setPrefix("Bearer")
				.buildAuthFilter()));
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(JwtUser.class));
		environment.jersey().register(RolesAllowedDynamicFeature.class);


		environment.jersey().register(new DefaultExceptionMapper());

		environment.jersey().register(new ApiListingResource());
		environment.jersey().register(new SwaggerSerializers());
		environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

		BeanConfig config = new BeanConfig();
		config.setTitle("CentauriCloud RestAPI");
		config.setVersion("1.0.0");
		config.setResourcePackage("org.centauri.cloud.rest.resource");
		config.setScan(true);
		environment.jersey().register(new CORSResponseFilter());

		environment.jersey().register(new UserResource());
		environment.jersey().register(new NetworkResource());
		environment.jersey().register(new ServerResource());
		environment.jersey().register(new TemplatesResource());
		environment.jersey().register(new CloudResource());
		environment.jersey().register(new UtilityResource());
		Cloud.getLogger().info("run stop");
	}
}
