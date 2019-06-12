package com.igt.ww.risk.prototype.domain.serialization;

import java.time.*;
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
		out.writeVarLong(bet.getHash(), false);
		out.writeString(bet.getAccountId());
		out.writeString(bet.getPlayerId());
		out.writeVarLong(serializeLocalDateTime(bet.getPlacementTime()), false);
		out.writeVarLong(serializeLocalDateTime(bet.getClosureTime()), false);
		out.writeString(bet.getBetType());
		out.writeString(bet.getState());
		out.writeDouble(bet.getStake());
		out.writeDouble(bet.getMaxReturn());
		out.writeDouble(bet.getBonus());
		List<BetLeg> legs = bet.getLegs();
		out.writeVarInt(legs.size(), true);
		for (BetLeg leg : legs)
			serializeBetLeg(leg, out);
		List<UnitRisk> unitRisks = bet.getUnitRisks();
		out.writeVarInt(unitRisks.size(), true);
		for (UnitRisk unitRisk : unitRisks)
			serializeUnitRisk(out, unitRisk);
		return out.toBytes();
	}

	private static void serializeBetLeg(BetLeg leg, Output out) {
		out.writeVarLong(leg.getSelectionId(), true);
		out.writeVarLong(leg.getMarketId(), true);
		out.writeVarLong(leg.getEventId(), true);
		out.writeVarLong(leg.getCompetitionId(), true);
		out.writeString(leg.getMarketTypeId());
		out.writeString(leg.getSportId());
		out.writeString(leg.getRiskClass());
		out.writeDouble(leg.getPrice());
		out.writeShort(leg.getMaxWinners());
		out.writeString(leg.getIrTag());
		out.writeString(leg.getState());
		LegRisk legRisk = leg.getLegRisk();
		out.writeBoolean(legRisk != null);
		if (legRisk != null) {
			out.writeVarLong(legRisk.getSinglesUnitCount(), true);
			out.writeDouble(legRisk.getSinglesStake());
			out.writeDouble(legRisk.getSinglesClosedReturn());
			out.writeDouble(legRisk.getSinglesMaxReturn());
			out.writeDouble(legRisk.getSinglesBonus());
			out.writeVarLong(legRisk.getUnitCount(), true);
			out.writeDouble(legRisk.getAbsoluteStake());
			out.writeDouble(legRisk.getContributedStake());
			out.writeDouble(legRisk.getContributedMaxReturn());
		}
	}

	private static void serializeUnitRisk(Output out, UnitRisk unitRisk) {
		long[] selectionIds = unitRisk.getSelectionIds();
		out.writeShort(selectionIds.length);
		out.writeLongs(selectionIds, true);
		out.writeDouble(unitRisk.getStake());
		out.writeDouble(unitRisk.getRunningOnStake());
		out.writeDouble(unitRisk.getMaxReturn());
		out.writeDouble(unitRisk.getBonus());
	}

	private static long serializeLocalDateTime(LocalDateTime dateTime) {
		return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
	}
}
