package org.centauri.cloud.rest.backpoint;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class BackpointManager {

	@Getter private Set<Backpoint> backpoints = new HashSet<>();

	public void register(Backpoint backpoint) {
		backpoints.add(backpoint);
		backpoint.register();
		System.out.println(backpoint.getPath());
	}

}
