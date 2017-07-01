package org.centauri.cloud.rest.jwt;

import lombok.Getter;

public class AuthException extends Exception {

	@Getter private int status;

	public AuthException(String message, int status) {
		super(message);
		this.status = status;
	}
}
