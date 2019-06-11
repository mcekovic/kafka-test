package com.igt.ww.risk.prototype.domain.serialization;

import java.util.*;

import org.apache.kafka.common.serialization.*;

import com.esotericsoftware.kryo.io.*;
import com.igt.ww.risk.prototype.domain.*;

public class BetSerializer extends BetSerde implements Serializer<Bet> {

	private static final int BUFFER_SIZE = 4096;

	@Override public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override public byte[] serialize(String topic, Bet bet) {
		Output out = new Output(BUFFER_SIZE);
		kryo.writeObject(out, bet);
		return out.toBytes();
	}
}
