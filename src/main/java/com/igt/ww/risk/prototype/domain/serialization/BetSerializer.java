package com.igt.ww.risk.prototype.domain.serialization;

import java.util.*;

import org.apache.kafka.common.serialization.*;

import com.esotericsoftware.kryo.io.*;
import com.igt.ww.risk.prototype.domain.*;

public class BetSerializer extends BetSerde implements Serializer<Bet> {

	private static final int BUFFER_SIZE = 2048;

	@Override public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override public byte[] serialize(String topic, Bet bet) {
		Output out = new Output(BUFFER_SIZE);
		out.writeVarLong(bet.getBetId(), true);
		out.writeString(bet.getBetType());
		out.writeDouble(bet.getStake());
		out.writeDouble(bet.getMaxReturn());
		List<BetLeg> legs = bet.getLegs();
		out.writeVarInt(legs.size(), true);
		for (BetLeg leg : legs)
			serializeBetLeg(leg, out);
		out.writeVarInt(legs.size(), true);
		List<UnitRisk> unitRisks = bet.getUnitRisks();
		for (UnitRisk unitRisk : unitRisks)
			serializeUnitRisk(out, unitRisk);
		return out.toBytes();
	}

	private void serializeBetLeg(BetLeg leg, Output out) {
		out.writeVarLong(leg.getSelectionId(), true);
		out.writeVarLong(leg.getMarketId(), true);
		out.writeVarLong(leg.getEventId(), true);
		out.writeDouble(leg.getPrice());
		out.writeShort(leg.getMaxWinners());
		out.writeString(leg.getIrTag());
		LegRisk legRisk = leg.getLegRisk();
		out.writeBoolean(legRisk != null);
		if (legRisk != null) {
			out.writeDouble(legRisk.getSinglesStake());
			out.writeDouble(legRisk.getAbsoluteStake());
			out.writeDouble(legRisk.getContributedStake());
			out.writeDouble(legRisk.getContributedMaxReturn());
		}
	}

	private void serializeUnitRisk(Output out, UnitRisk unitRisk) {
		long[] selectionIds = unitRisk.getSelectionIds();
		out.writeShort(selectionIds.length);
		out.writeLongs(selectionIds, true);
		out.writeDouble(unitRisk.getStake());
		out.writeDouble(unitRisk.getMaxReturn());
	}
}
