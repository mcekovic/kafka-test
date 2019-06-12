package com.igt.ww.risk.prototype.domain;

import lombok.*;

@Data
public class UnitRisk {

	private final long[] selectionIds;
	private double stake;
	private double runningOnStake;
	private double maxReturn;
	private double bonus;

	public void add(UnitRisk unitRisk) {
		stake += unitRisk.stake;
		runningOnStake += unitRisk.runningOnStake;
		maxReturn += unitRisk.maxReturn;
		bonus += unitRisk.bonus;
	}
}
