package com.igt.ww.risk.prototype;

import org.junit.jupiter.api.*;

import com.igt.ww.risk.prototype.domain.*;
import com.igt.ww.risk.prototype.domain.serialization.*;

import static com.igt.ww.risk.prototype.BetMakers.*;
import static org.assertj.core.api.Assertions.*;

class KryoSerdeTest {

	private static final String TOPIC = "TestTopic";

	@Test
	void betIsSerializedAndDeserialized() {
		serializeAndDeserializeBet(makeBet(), 200);
	}

	@Test
	void multipleBetIsSerializedAndDeserialized() {
		serializeAndDeserializeBet(makeMultipleBet(), 850);
	}

	private static void serializeAndDeserializeBet(Bet bet, int maxLength) {
		BetSerializer ser = new BetSerializer();
		byte[] data = ser.serialize(TOPIC, bet);
		assertThat(data.length).isLessThanOrEqualTo(maxLength);

		BetDeserializer deser = new BetDeserializer();
		Bet bet2 = deser.deserialize(TOPIC, data);

		assertThat(bet2).isEqualTo(bet);
		assertThat(bet2).isEqualToComparingFieldByField(bet);
	}
}
