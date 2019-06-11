package com.igt.ww.risk.prototype;

import java.util.concurrent.*;

import org.apache.kafka.clients.consumer.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.annotation.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.*;
import org.springframework.kafka.test.context.*;

import com.igt.ww.risk.prototype.domain.*;

import static com.igt.ww.risk.prototype.BetMakers.*;
import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.TestInstance.*;

@SpringBootTest
@EmbeddedKafka(ports = 9092)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmokeKafkaIT {

	private static final CountDownLatch counter = new CountDownLatch(1);

	private static final String TOPIC = "TestTopic";

	@TestConfiguration
	static class Config {

		@Bean
		MessageListener<Long, Bet> testListener() {
			return new MessageListener<Long, Bet>() {
				@KafkaListener(topics = TOPIC)
				@Override public void onMessage(ConsumerRecord<Long, Bet> record) {
					System.out.println("Message received: " + record);
					counter.countDown();
				}
			};
		}
	}

	@Autowired private KafkaTemplate<Long, Bet> kafkaTemplate;

	@Test @Order(1)
	void betIsSent() {
		Bet bet = makeBet();
		kafkaTemplate.send(TOPIC, bet);
		System.out.println("Message sent: " + bet);
	}

	@Test @Order(2)
	void betIsReceived() {
		await().until(() -> {
			counter.await();
			return true;
		});
	}
}
