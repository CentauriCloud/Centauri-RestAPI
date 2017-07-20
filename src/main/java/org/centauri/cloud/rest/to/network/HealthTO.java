package org.centauri.cloud.rest.to.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthTO {

	@Max(100)
	private double cpuUsagePercent;
	private int totalRam;
	private int availableRam;

}
