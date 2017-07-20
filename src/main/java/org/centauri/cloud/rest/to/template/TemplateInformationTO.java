package org.centauri.cloud.rest.to.template;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemplateInformationTO {

	private String id;
	private String name;
	private int players;
	private String[] plugins;
	private int packageSize;
	private int created;

}
