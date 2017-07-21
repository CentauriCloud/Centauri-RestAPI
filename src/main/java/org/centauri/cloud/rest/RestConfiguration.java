package org.centauri.cloud.rest;

import io.dropwizard.Configuration;
import lombok.Getter;

@Getter
public class RestConfiguration extends Configuration {

	private boolean devMode;
}
