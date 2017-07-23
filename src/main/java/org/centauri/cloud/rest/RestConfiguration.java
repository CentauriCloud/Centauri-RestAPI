package org.centauri.cloud.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import lombok.Getter;

@Getter
public class RestConfiguration extends Configuration implements AssetsBundleConfiguration {

	@JsonProperty
	private final AssetsConfiguration assets = AssetsConfiguration.builder().build();

	@Override
	public AssetsConfiguration getAssetsConfiguration() {
		return assets;
	}
}
