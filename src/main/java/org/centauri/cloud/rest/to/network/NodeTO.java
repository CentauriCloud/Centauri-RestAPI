package org.centauri.cloud.rest.to.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NodeTO {

	private int daemons;
	private int bungeecords;
	private int spigotServers;

}
