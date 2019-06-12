package com.igt.ww.risk.prototype;

import java.util.concurrent.*;

import org.apache.kafka.clients.admin.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.test.context.*;
import org.springframework.util.*;

import com.igt.ww.risk.prototype.domain.*;

import static com.igt.ww.risk.prototype.BetMakers.*;
import static com.igt.ww.risk.prototype.domain.RiskAggregator.*;
import static java.lang.String.*;
import static org.awaitility.Awaitility.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.TestInstance.*;

@SpringBootTest
@EmbeddedKafka(ports = {9092, 9192, 9292}, count = 3)
@TestInstance(Lifecycle.PER_CLASS)
class RiskAggregationPT {

	private static final int PARTITIONS = 257;

	private static final long BET_COUNT = 1000000L;
	private static final long BET_CHUNK = 100000L;

	@Autowired private KafkaTemplate<Long, Bet> kafkaTemplate;
	@Autowired private RiskAggregator riskAggregator;
	@Autowired private KafkaListenerEndpointRegistry listenerRegistry;

	private StopWatch stopWatch;

	@TestConfiguration
	static class Config {

		@Bean
		public NewTopic betTopic() {
			return new NewTopic(TOPIC_NAME, PARTITIONS, (short) 2);
		}
	}

	@BeforeAll
	void betsFixture() throws InterruptedException {
		MessageListenerContainer listenerContainer = listenerRegistry.getListenerContainer(LISTENER_ID);
		listenerContainer.pause();
		System.out.print("Sending messages");
		StopWatch sendStopWatch = new StopWatch(format("%d bets sending", BET_COUNT));
		sendStopWatch.start();
		for (long betId = 1; betId <= BET_COUNT; betId++) {
			kafkaTemplate.send(TOPIC_NAME, createBet(betId));
			if (betId % BET_CHUNK == 0)
				System.out.printf("%d Messages sent.%n", betId);
		}
		sendStopWatch.stop();
		System.out.println(sendStopWatch);

		TimeUnit.SECONDS.sleep(5);

		System.out.print("Receiving messages");
		stopWatch = new StopWatch(format("%d bets aggregation", BET_COUNT));
		stopWatch.start();
		listenerContainer.resume();
	}

	private Bet createBet(long betId) {
		return betId % 3 == 0 ? makeBet(betId) : makeMultipleBet(betId);
	}

	@Test
	void countIsAggregated() {
		await().forever().until(() -> riskAggregator.getCount(), equalTo(BET_COUNT));
	}

	@Test
	void stakeIsAggregated() {
		await().forever().until(() -> riskAggregator.getStake(), equalTo(BET_COUNT * 10.0));
	}

	@AfterAll
	void measureTime() {
		stopWatch.stop();
		System.out.println(stopWatch);
	}
}
