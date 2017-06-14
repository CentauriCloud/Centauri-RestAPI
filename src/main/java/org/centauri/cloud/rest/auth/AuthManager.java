package org.centauri.cloud.rest.auth;

public class AuthManager {

	public boolean validate(String user) {
		return true;
	}

	public boolean hasPermission(String site, String user) {
		return true;
	}

}
