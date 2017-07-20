package org.centauri.cloud.rest.to.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.centauri.cloud.common.network.server.ServerType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerInformationTO {

	private String id;
	private String host;
	private int started;
	private long lastPing;
	private ServerType type;
	private int players = -1;

}
