package org.centauri.cloud.rest.to.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationTO {

	private Integer id;
	@Size(max = 30)
	@NotNull
	private String username;
	@Email
	@NotNull
	@Size(max = 30)
	private String email;
	@NotNull
	@Size(max = 30)
	private String password;
	private List<String> customPermissions;
	private int userGroupId;
	private long lastLogin;
	private boolean active;

}
