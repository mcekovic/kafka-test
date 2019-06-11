package com.igt.ww.risk.prototype.domain;

import lombok.*;

@Data
public class LegRisk {

	private double singlesStake;
	private double absoluteStake;
	private double contributedStake;
	private double contributedMaxReturn;

	public void add(LegRisk legRisk) {
		singlesStake += legRisk.singlesStake;
		absoluteStake += legRisk.absoluteStake;
		contributedStake += legRisk.contributedStake;
		contributedMaxReturn += legRisk.contributedMaxReturn;
	}
}
