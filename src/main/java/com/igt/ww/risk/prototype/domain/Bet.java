package com.igt.ww.risk.prototype.domain;

import java.util.*;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Bet {

	private final long betId;
	private String betType;
	private double stake;
	private double maxReturn;
	private List<BetLeg> legs;
	private List<UnitRisk> unitRisks;
}
