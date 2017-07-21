package org.centauri.cloud.rest.to.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthTO {

	private String email;
	private String password;

}
