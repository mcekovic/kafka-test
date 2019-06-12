package com.igt.ww.risk.prototype.domain.serialization;

import java.time.*;
import java.util.*;

import org.apache.kafka.common.serialization.*;

import com.esotericsoftware.kryo.io.*;
import com.igt.ww.risk.prototype.domain.*;

public class BetDeserializer extends BetSerde implements Deserializer<Bet> {

	@Override public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override public Bet deserialize(String topic, byte[] data) {
		Input in = new Input(data);
		Bet bet = new Bet(in.readLong(true));
		bet.setHash(in.readLong(false));
		bet.setAccountId(in.readString());
		bet.setPlayerId(in.readString());
		bet.setPlacementTime(toLocalDateTime(in.readLong(false)));
		bet.setClosureTime(toLocalDateTime(in.readLong(false)));
		bet.setBetType(in.readString());
		bet.setState(in.readString());
		bet.setStake(in.readDouble());
		bet.setMaxReturn(in.readDouble());
		bet.setBonus(in.readDouble());
		int legCount = in.readInt(true);
		List<BetLeg> legs = new ArrayList<>(legCount);
		for (int i = 0; i < legCount; i++)
			legs.add(deserializeBetLeg(in));
		bet.setLegs(legs);
		int unitRiskCount = in.readInt(true);
		List<UnitRisk> unitRisks = new ArrayList<>(unitRiskCount);
		for (int i = 0; i < unitRiskCount; i++)
			unitRisks.add(deserializeUnitRisk(in));
		bet.setUnitRisks(unitRisks);
		return bet;
	}

	private static BetLeg deserializeBetLeg(Input in) {
		BetLeg leg = new BetLeg(in.readLong(true));
		leg.setMarketId(in.readLong(true));
		leg.setEventId(in.readLong(true));
		leg.setCompetitionId(in.readLong(true));
		leg.setMarketTypeId(in.readString());
		leg.setSportId(in.readString());
		leg.setRiskClass(in.readString());
		leg.setPrice(in.readDouble());
		leg.setMaxWinners(in.readShort());
		leg.setIrTag(in.readString());
		leg.setState(in.readString());
		if (in.readBoolean()) {
			LegRisk legRisk = new LegRisk();
			legRisk.setSinglesUnitCount(in.readVarLong(true));
			legRisk.setSinglesStake(in.readDouble());
			legRisk.setSinglesClosedReturn(in.readDouble());
			legRisk.setSinglesMaxReturn(in.readDouble());
			legRisk.setSinglesBonus(in.readDouble());
			legRisk.setUnitCount(in.readVarLong(true));
			legRisk.setAbsoluteStake(in.readDouble());
			legRisk.setContributedStake(in.readDouble());
			legRisk.setContributedMaxReturn(in.readDouble());
			leg.setLegRisk(legRisk);
		}
		return leg;
	}

	private static UnitRisk deserializeUnitRisk(Input in) {
		UnitRisk unitRisk = new UnitRisk(in.readLongs((int) in.readShort(), true));
		unitRisk.setStake(in.readDouble());
		unitRisk.setRunningOnStake(in.readDouble());
		unitRisk.setMaxReturn(in.readDouble());
		unitRisk.setBonus(in.readDouble());
		return unitRisk;
	}

	private static LocalDateTime toLocalDateTime(long epochMilli) {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneOffset.UTC);
	}
}
