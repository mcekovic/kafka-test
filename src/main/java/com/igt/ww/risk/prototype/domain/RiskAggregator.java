package com.igt.ww.risk.prototype.domain;

import java.util.*;

import org.apache.kafka.clients.consumer.*;
import org.springframework.kafka.annotation.*;
import org.springframework.stereotype.*;

@Service
public class RiskAggregator {

	public static final String LISTENER_ID = "Bet";
	public static final String TOPIC_NAME = "Bet";

	private long count;
	private double stake;
	private double maxReturn;
	private Map<Long, LegRisk> selectionRisks = new HashMap<>();
	private Map<long[], UnitRisk> unitRisks = new HashMap<>();

	@KafkaListener(id = LISTENER_ID, topics = TOPIC_NAME)
	public synchronized void onMessage(ConsumerRecord<Long, Bet> record) {
		Bet bet = record.value();
		count++;
		stake += bet.getStake();
		maxReturn += bet.getMaxReturn();
		for (BetLeg leg : bet.getLegs())
			selectionRisks.computeIfAbsent(leg.getSelectionId(), selectionId -> new LegRisk()).add(leg.getLegRisk());
		for (UnitRisk unitRisk : bet.getUnitRisks())
			unitRisks.computeIfAbsent(unitRisk.getSelectionIds(), selectionIds -> new UnitRisk()).add(unitRisk);
	}

	public synchronized long getCount() {
		return count;
	}

	public synchronized double getStake() {
		return stake;
	}

	public synchronized double getMaxReturn() {
		return maxReturn;
	}

	public synchronized LegRisk getLegRisk(long selectionId) {
		return selectionRisks.get(selectionId);
	}

	public synchronized UnitRisk getUnitRisk(long[] selectionIds) {
		return unitRisks.get(selectionIds);
	}
}
