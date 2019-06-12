package com.igt.ww.risk.prototype.domain;

import lombok.*;

@Data
public class LegRisk {

	private long singlesUnitCount;
	private double singlesStake;
	private double singlesClosedReturn;
	private double singlesMaxReturn;
	private double singlesBonus;

	private long unitCount;
	private double absoluteStake;
	private double contributedStake;
	private double contributedMaxReturn;

	public void add(LegRisk legRisk) {
		singlesUnitCount += legRisk.singlesUnitCount;
		singlesStake += legRisk.singlesStake;
		singlesClosedReturn += legRisk.singlesClosedReturn;
		singlesMaxReturn += legRisk.singlesMaxReturn;
		singlesBonus += legRisk.singlesBonus;

		unitCount += legRisk.unitCount;
		absoluteStake += legRisk.absoluteStake;
		contributedStake += legRisk.contributedStake;
		contributedMaxReturn += legRisk.contributedMaxReturn;
	}
}
