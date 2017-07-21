package org.centauri.cloud.rest.to.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationTO {

	private String username;
	private String email;
	private String password;
	private String[] customPermissions;
	private String userGroup;
	private long lastLogin;
	private boolean active;

}
