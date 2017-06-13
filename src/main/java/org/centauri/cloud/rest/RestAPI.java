package org.centauri.cloud.rest;

import lombok.Getter;
import org.centauri.cloud.cloud.plugin.AbstractModule;
import org.centauri.cloud.rest.backpoint.BackpointAuthentication;
import org.centauri.cloud.rest.backpoint.BackpointManager;

import java.io.File;

import static spark.Spark.staticFiles;
import static spark.Spark.stop;

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
		staticFiles.externalLocation("files");
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
