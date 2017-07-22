package org.centauri.cloud.rest.auth.jwt;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JwtUser implements Principal {
	private final String name;

	private final Set<String> roles;

	public JwtUser(String name) {
		this(name, null);
	}

	public JwtUser(String name, List<String> roles) {
		this.name = name;
		this.roles = new HashSet<>(roles);
	}

	public String getName() {
		return name;
	}

	public Set<String> getRoles() {
		return roles;
	}
}
