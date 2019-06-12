package com.igt.ww.risk.prototype;

import java.time.*;

import com.igt.ww.risk.prototype.domain.*;

import static java.util.Arrays.*;
import static java.util.Collections.*;

public interface BetMakers {

	static Bet makeBet() {
		return makeBet(1L);
	}

	static Bet makeBet(long betId) {
		Bet bet = new Bet(betId);
		bet.setHash(1234567890L);
		bet.setPlayerId("ABCDEFGH");
		bet.setAccountId("12345678");
		LocalDateTime placementTime = LocalDateTime.now();
		bet.setPlacementTime(placementTime);
		bet.setClosureTime(placementTime.plusDays(1));
		bet.setBetType("S");
		bet.setState("O");
		bet.setStake(10.0);
		bet.setMaxReturn(20.0);
		bet.setBonus(0.0);
		bet.setLegs(singletonList(
			makeBetLeg(12345671L, 1234561L, 123451L, 12345L, "FBL1X2", "FBL", "A", 2.0, (short)1, "O:SCORE", "O", makeSingleBetLegRisk(1, 10.0, 0.0, 20.0, 0.0))
		));
		bet.setUnitRisks(emptyList());
		return bet;
	}

	static Bet makeMultipleBet() {
		return makeMultipleBet(1L);
	}

	static Bet makeMultipleBet(long betId) {
		Bet bet = new Bet(betId);
		bet.setHash(1234567891L);
		bet.setPlayerId("ABCDEFGH");
		bet.setAccountId("12345678");
		LocalDateTime placementTime = LocalDateTime.now();
		bet.setPlacementTime(placementTime);
		bet.setClosureTime(placementTime.plusDays(1));
		bet.setBetType("M");
		bet.setState("O");
		bet.setStake(10.0);
		bet.setMaxReturn(200.0);
		bet.setBonus(10.0);
		bet.setLegs(asList(
			makeBetLeg(12345671L, 1234561L, 123451L, 12345L, "FBL1X2", "FBL", "A", 2.0, (short)1, "O:SCORE", "O", makeMultipleBetLegRisk(5L, 10.0, 2.0, 30.0)),
			makeBetLeg(12345672L, 1234562L, 123452L, 12345L, "FBL1X2", "FBL", "A", 3.0, (short)1, "O:SCORE", "O", makeMultipleBetLegRisk(5L, 10.0, 2.0, 40.0)),
			makeBetLeg(12345673L, 1234563L, 123453L, 12345L, "FBL1X2", "FBL", "A", 4.0, (short)1, "O:SCORE", "O", makeMultipleBetLegRisk(5L, 10.0, 2.0, 50.0)),
			makeBetLeg(12345674L, 1234564L, 123454L, 12345L, "FBL1X2", "FBL", "A", 5.0, (short)1, "O:SCORE", "O", makeMultipleBetLegRisk(5L, 10.0, 2.0, 60.0)),
			makeBetLeg(12345675L, 1234565L, 123455L, 12345L, "FBL1X2", "FBL", "A", 6.0, (short)1, "O:SCORE", "O", makeMultipleBetLegRisk(5L, 10.0, 2.0, 70.0))
		));
		bet.setUnitRisks(asList(
			makeUnitRisk(new long[] {12345671L, 12345672L, 12345673L, 12345674L}, 10.0, 10.0, 120.0, 2.0),
			makeUnitRisk(new long[] {12345671L, 12345672L, 12345673L, 12345675L}, 10.0, 10.0, 144.0, 3.0),
			makeUnitRisk(new long[] {12345671L, 12345672L, 12345674L, 12345675L}, 10.0, 10.0, 180.0, 4.0),
			makeUnitRisk(new long[] {12345671L, 12345673L, 12345674L, 12345675L}, 10.0, 10.0, 240.0, 5.0),
			makeUnitRisk(new long[] {12345672L, 12345673L, 12345674L, 12345675L}, 10.0, 10.0, 360.0, 6.0)
		));
		return bet;
	}

	static BetLeg makeBetLeg(long selectionId, long marketId, long eventId, long competitionId, String marketTypeId, String sportId, String riskClass, double price, short maxWinners, String irTag, String state, LegRisk legRisk) {
		BetLeg leg = new BetLeg(selectionId);
		leg.setMarketId(marketId);
		leg.setEventId(eventId);
		leg.setCompetitionId(competitionId);
		leg.setMarketTypeId(marketTypeId);
		leg.setSportId(sportId);
		leg.setRiskClass(riskClass);
		leg.setPrice(price);
		leg.setMaxWinners(maxWinners);
		leg.setIrTag(irTag);
		leg.setState(state);
		leg.setLegRisk(legRisk);
		return leg;
	}

	static LegRisk makeSingleBetLegRisk(long singlesUnitCount, double singlesStake, double singlesClosedReturn, double singlesMaxReturn, double singlesBonus) {
		LegRisk legRisk = new LegRisk();
		legRisk.setSinglesUnitCount(singlesUnitCount);
		legRisk.setSinglesStake(singlesStake);
		legRisk.setSinglesClosedReturn(singlesClosedReturn);
		legRisk.setSinglesMaxReturn(singlesMaxReturn);
		legRisk.setSinglesBonus(singlesBonus);
		legRisk.setUnitCount(singlesUnitCount);
		legRisk.setAbsoluteStake(singlesStake);
		legRisk.setContributedStake(singlesStake);
		legRisk.setContributedMaxReturn(singlesMaxReturn);
		return legRisk;
	}

	static LegRisk makeMultipleBetLegRisk(long unitCount, double absoluteStake, double contributedStake, double contributedMaxReturn) {
		LegRisk legRisk = new LegRisk();
		legRisk.setUnitCount(unitCount);
		legRisk.setAbsoluteStake(absoluteStake);
		legRisk.setContributedStake(contributedStake);
		legRisk.setContributedMaxReturn(contributedMaxReturn);
		return legRisk;
	}

	static UnitRisk makeUnitRisk(long[] selectionIds, double stake, double runningOnStake, double maxReturn, double bonus) {
		UnitRisk unitRisk = new UnitRisk(selectionIds);
		unitRisk.setStake(stake);
		unitRisk.setRunningOnStake(runningOnStake);
		unitRisk.setMaxReturn(maxReturn);
		unitRisk.setBonus(bonus);
		return unitRisk;
	}
}
