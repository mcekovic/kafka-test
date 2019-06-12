package com.igt.ww.risk.prototype.domain;

import lombok.*;

@Data
public class BetLeg {

	private final long selectionId;
	private long marketId;
	private long eventId;
	private long competitionId;
	private String marketTypeId;
	private String sportId;
	private String riskClass;
	private double price;
	private short maxWinners;
	private String irTag;
	private String state;
	private LegRisk legRisk;
}
