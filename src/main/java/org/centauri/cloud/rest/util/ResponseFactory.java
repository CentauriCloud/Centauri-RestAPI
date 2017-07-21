package org.centauri.cloud.rest.util;

import javax.ws.rs.core.Response;

public class ResponseFactory {

	public static Response ok() {
		return Response.status(200).entity(MapUtil.from("status", "SUCCESS")).build();
	}

	public static Response ok(Object data) {
		return Response.status(200).entity(MapUtil.builder().add("status", "SUCCESS").add("data", data).build()).build();
	}

	public static Response fail() {
		return Response.status(200).entity(MapUtil.from("status", "FAILURE")).build();
	}

	public static Response fail(Object data) {
		return Response.status(200).entity(MapUtil.builder().add("status", "FAILURE").add("data", data).build()).build();
	}

}
