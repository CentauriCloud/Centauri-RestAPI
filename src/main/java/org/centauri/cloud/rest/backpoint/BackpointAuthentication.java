package org.centauri.cloud.rest.backpoint;

import spark.Route;

public class BackpointAuthentication extends Backpoint {

	public BackpointAuthentication() {
		String path = "/auth";
		Route route = (request, response) -> "Hallo";
		setPath(path);
		setRoute(route);
	}
}
