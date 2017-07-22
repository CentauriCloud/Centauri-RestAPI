package org.centauri.cloud.rest.to.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationTO {

	private Integer id;
	@Size(max = 30)
	@NotEmpty
	private String username;
	@Email
	@NotEmpty
	@Size(max = 30)
	private String email;
	@NotEmpty
	@Size(max = 30)
	private String password;
	private List<String> customPermissions;
	private int userGroupId;
	private long lastLogin;
	private boolean active;

}
