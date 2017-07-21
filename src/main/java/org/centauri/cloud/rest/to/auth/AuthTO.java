package org.centauri.cloud.rest.to.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthTO {

	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;

}
