package org.centauri.cloud.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import lombok.Getter;

@Getter
public class RestConfiguration extends Configuration {


	@JsonProperty("swagger")
	public SwaggerBundleConfiguration swaggerBundleConfiguration;
}
