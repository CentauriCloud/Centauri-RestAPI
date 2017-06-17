package org.centauri.cloud.rest;

import lombok.Getter;
import org.centauri.cloud.cloud.plugin.AbstractModule;
import org.centauri.cloud.rest.auth.AuthManager;
import org.centauri.cloud.rest.backpoint.BackpointAuthentication;
import org.centauri.cloud.rest.backpoint.BackpointManager;
import org.pac4j.sparkjava.SecurityFilter;

import java.io.File;

import static spark.Spark.*;

public class RestAPI extends AbstractModule {

	@Getter private static RestAPI instance;
	@Getter private BackpointManager backpointManager;

	@Override
	public String getName() {
		return "RestAPI";
	}

	@Override
	public String getVersion() {
		return "1.0-SNAPSHOT";
	}

	@Override
	public String getAuthor() {
		return "Centauri Developer Team";
	}

	@Override
	public void onEnable() {
		instance = this;
		new File("files").mkdir();
		AuthManager manager = new AuthManager();
		staticFiles.externalLocation("files");
		//TODO permissions

		before("/auth", new SecurityFilter(manager.getConfig(), "IndirectBasicAuthClient"));
		get("/auth", manager::jwt);

		path("/api", () -> {
			before("/*", new SecurityFilter(manager.getConfig(), "ParameterClient"));


		});

		after((request, response) -> {
			response.type("application/json");
			response.header("Content-Encoding", "gzip");
		});
		loadBackpoints();
	}

	@Override
	public void onDisable() {
		stop();
	}


	private void loadBackpoints() {
		new Thread(() -> {
			backpointManager = new BackpointManager();
			backpointManager.register(new BackpointAuthentication());
		}).start();
	}
}
