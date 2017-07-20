package org.centauri.cloud.rest.to.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.centauri.cloud.common.network.server.ServerType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerTO {

	private String prefix;
	private ServerType type;

}