package com.igt.ww.risk.prototype.domain.serialization;

import java.util.*;

import org.apache.kafka.common.serialization.*;

import com.esotericsoftware.kryo.io.*;
import com.igt.ww.risk.prototype.domain.*;

public class BetDeserializer extends BetSerde implements Deserializer<Bet> {

	@Override public void configure(Map<String, ?> configs, boolean isKey) {}

	@Override public Bet deserialize(String topic, byte[] data) {
		return kryo.readObject(new Input(data), Bet.class);
	}
}
