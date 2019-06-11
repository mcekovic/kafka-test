package com.igt.ww.risk.prototype.domain;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UnitRisk {

	private final long[] selectionIds;
	private double stake;
	private double maxReturn;

	public void add(UnitRisk unitRisk) {
		stake += unitRisk.stake;
		maxReturn += unitRisk.maxReturn;
	}
}
