package org.centauri.cloud.rest.util;

import javax.ws.rs.core.Response;

public class ResponseFactory {

	public static Response ok() {
		return Response.status(200).entity(MapUtil.from("Status", "SUCCESS")).build();
	}

	public static Response ok(Object data) {
		return Response.status(200).entity(MapUtil.from("Status", "SUCCESS")).entity(data).build();
	}

	public static Response fail() {
		return Response.status(200).entity(MapUtil.from("Status", "FAILURE")).build();
	}

	public static Response fail(Object data) {
		return Response.status(200).entity(MapUtil.from("Status", "FAILURE")).entity(data).build();
	}

}
