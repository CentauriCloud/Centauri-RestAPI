package org.centauri.cloud.rest.to.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMetricsTO {

	private int playerCount;
	private int activeNodes;
	private int timestamp;

}
