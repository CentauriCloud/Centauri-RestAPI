package org.centauri.cloud.rest.to.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {

	private String username;
	private String password;
	private boolean active;

}
