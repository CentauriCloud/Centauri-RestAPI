package org.centauri.cloud.rest.to.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTO {

	private String newVersion;
	private String updateUrl;
	private int releaseDate;

}
