package org.centauri.cloud.rest.to.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupInformationTO {

	private Integer id;
	@Size(max = 30)
	@NotEmpty
	private String name;
	private String description;
	private boolean active;
	private String[] permissions;
}
