package com.igt.ww.risk.prototype.domain;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class BetLeg {

	private final long selectionId;
	private long marketId;
	private long eventId;
	private double price;
	private short maxWinners;
	private String irTag;
	private LegRisk legRisk;
}
