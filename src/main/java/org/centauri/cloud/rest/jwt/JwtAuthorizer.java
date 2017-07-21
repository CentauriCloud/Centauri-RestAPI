package org.centauri.cloud.rest.jwt;

import io.dropwizard.auth.Authorizer;

import java.security.Principal;

public class JwtAuthorizer implements Authorizer<Principal> {
	@Override
	public boolean authorize(Principal jwtUser, String role) {
		System.out.println(((JwtUser) jwtUser).getRoles());
		System.out.println(role);
		return ((JwtUser) jwtUser).getRoles().contains(role);
	}
}
