package com.igt.ww.risk.prototype;

import java.util.*;

import com.igt.ww.risk.prototype.domain.*;

import static java.util.Arrays.*;

public interface BetMakers {

	static Bet makeBet() {
		return makeBet(1L);
	}

	static Bet makeBet(long betId) {
		Bet bet = new Bet(betId);
		bet.setBetType("S");
		bet.setStake(10.0);
		bet.setMaxReturn(20.0);
		bet.setLegs(new ArrayList<>(asList(
			makeBetLeg(12345671L, 1234561L, 123451L, 2.0, (short)1, "O:SCORE", makeBetLegRisk(10.0, 20.0, 15.0, 30.0)),
			makeBetLeg(12345672L, 1234562L, 123452L, 3.0, (short)1, "O:SCORE", makeBetLegRisk(20.0, 30.0, 25.0, 40.0)),
			makeBetLeg(12345673L, 1234563L, 123453L, 4.0, (short)1, "O:SCORE", makeBetLegRisk(30.0, 40.0, 35.0, 50.0)),
			makeBetLeg(12345674L, 1234564L, 123454L, 5.0, (short)1, "O:SCORE", makeBetLegRisk(40.0, 50.0, 45.0, 60.0)),
			makeBetLeg(12345675L, 1234565L, 123455L, 6.0, (short)1, "O:SCORE", makeBetLegRisk(50.0, 60.0, 55.0, 70.0))
		)));
		bet.setUnitRisks(new ArrayList<>(asList(
			makeUnitRisk(new long[] {12345671L, 12345672L, 12345673L, 12345674L}, 10.0, 120.0),
			makeUnitRisk(new long[] {12345671L, 12345672L, 12345673L, 12345675L}, 10.0, 144.0),
			makeUnitRisk(new long[] {12345671L, 12345672L, 12345674L, 12345675L}, 10.0, 180.0),
			makeUnitRisk(new long[] {12345671L, 12345673L, 12345674L, 12345675L}, 10.0, 240.0),
			makeUnitRisk(new long[] {12345672L, 12345673L, 12345674L, 12345675L}, 10.0, 360.0)
		)));
		return bet;
	}

	static BetLeg makeBetLeg(long selectionId, long marketId, long eventId, double price, short maxWinners, String irTag, LegRisk legRisk) {
		BetLeg leg = new BetLeg(selectionId);
		leg.setMarketId(marketId);
		leg.setEventId(eventId);
		leg.setPrice(price);
		leg.setMaxWinners(maxWinners);
		leg.setIrTag(irTag);
		leg.setLegRisk(legRisk);
		return leg;
	}

	static LegRisk makeBetLegRisk(double singlesStake, double absoluteStake, double contributedStake, double contributedMaxReturn) {
		LegRisk legRisk = new LegRisk();
		legRisk.setSinglesStake(singlesStake);
		legRisk.setAbsoluteStake(absoluteStake);
		legRisk.setContributedStake(contributedStake);
		legRisk.setContributedMaxReturn(contributedMaxReturn);
		return legRisk;
	}

	static UnitRisk makeUnitRisk(long[] selectionIds, double stake, double maxReturn) {
		UnitRisk unitRisk = new UnitRisk(selectionIds);
		unitRisk.setStake(stake);
		unitRisk.setMaxReturn(maxReturn);
		return unitRisk;
	}
}
