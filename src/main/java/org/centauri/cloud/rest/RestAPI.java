package org.centauri.cloud.rest;

import com.google.gson.Gson;
import lombok.Getter;
import org.centauri.cloud.cloud.plugin.AbstractModule;
import org.centauri.cloud.rest.auth.AuthManager;
import org.centauri.cloud.rest.backpoint.BackpointAuthentication;
import org.centauri.cloud.rest.backpoint.BackpointManager;

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
		before("*", (request, response) -> {
			boolean valid = manager.validate("");
			if (!valid) {
				halt(401, "Not Allowed");
				return;
			}
			boolean allowed = manager.hasPermission(request.contextPath(), "");
			if (!allowed) {
				halt(403, "Not Allowed");
				return;
			}
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
