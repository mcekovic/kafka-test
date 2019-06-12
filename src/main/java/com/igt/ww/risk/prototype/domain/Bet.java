package com.igt.ww.risk.prototype.domain;

import java.time.*;
import java.util.*;

import lombok.*;

@Data
public class Bet {

	private final long betId;
	private long hash;
	private String accountId;
	private String playerId;
	private LocalDateTime placementTime;
	private LocalDateTime closureTime;

	private String betType;
	private String state;
	private double stake;
	private double maxReturn;
	private double bonus;
	private List<BetLeg> legs;
	private List<UnitRisk> unitRisks;
}
