package com.igt.ww.risk.prototype.domain.serialization;

import java.util.*;

import org.apache.kafka.common.serialization.*;

import com.esotericsoftware.kryo.io.*;
import com.igt.ww.risk.prototype.domain.*;

public class BetDeserializer extends BetSerde implements Deserializer<Bet> {

	@Override public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override public Bet deserialize(String topic, byte[] data) {
		Input in = new Input(data);
		Bet bet = new Bet(in.readLong(true));
		bet.setBetType(in.readString());
		bet.setStake(in.readDouble());
		bet.setMaxReturn(in.readDouble());
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

	private BetLeg deserializeBetLeg(Input in) {
		BetLeg leg = new BetLeg(in.readLong(true));
		leg.setMarketId(in.readLong(true));
		leg.setEventId(in.readLong(true));
		leg.setPrice(in.readDouble());
		leg.setMaxWinners(in.readShort());
		leg.setIrTag(in.readString());
		if (in.readBoolean()) {
			LegRisk legRisk = new LegRisk();
			legRisk.setSinglesStake(in.readDouble());
			legRisk.setAbsoluteStake(in.readDouble());
			legRisk.setContributedStake(in.readDouble());
			legRisk.setContributedMaxReturn(in.readDouble());
			leg.setLegRisk(legRisk);
		}
		return leg;
	}

	private UnitRisk deserializeUnitRisk(Input in) {
		UnitRisk unitRisk = new UnitRisk(in.readLongs((int) in.readShort(), true));
		unitRisk.setStake(in.readDouble());
		unitRisk.setMaxReturn(in.readDouble());
		return unitRisk;
	}
}
