package org.centauri.cloud.rest.resource;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CORSResponseFilter implements ContainerResponseFilter {
	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		containerResponseContext.getHeaders().add("Access-Control-Max-Age", "3600");

	}
}
