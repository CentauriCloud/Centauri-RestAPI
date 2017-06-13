package org.centauri.cloud.rest.backpoint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spark.Route;

import static spark.Spark.get;

@NoArgsConstructor
@Setter
public abstract class Backpoint {

	@Getter private String path;
	private Route route;

	void register() {
		get(path, route);
	}

}
