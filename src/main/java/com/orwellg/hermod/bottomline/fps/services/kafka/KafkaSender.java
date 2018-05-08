package com.orwellg.hermod.bottomline.fps.services.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaSender {

	private static final Logger LOG = LogManager.getLogger(KafkaSender.class);

	@Autowired
	private KafkaWithHeadersTemplate<String, String> kafkaTemplate;

	public void send(String topic, String payload, String key, String replyTo, String environment, String paymentType,
					 boolean isPOO, Long qosMilliseconds) {
		LOG.info("[FPS][PmtId: {}] Sending payload='{}' to topic='{}'", key, payload, topic);
		kafkaTemplate.send(topic, payload, key, replyTo, environment, paymentType, isPOO, false, qosMilliseconds);
	}

	public void send(String topic, String payload, String key, String replyTo, String environment, String paymentType,
					 boolean isPOO, boolean isStandin, Long qosMilliseconds) {
		LOG.info("[FPS][PmtId: {}] Sending payload='{}' to topic='{}'", key, payload, topic);
		kafkaTemplate.send(topic, payload, key, replyTo, environment, paymentType, isPOO, isStandin, qosMilliseconds);
	}

	public void sendRawMessage(String topic, String payload, String key) {
		LOG.info("[FPS][PmtId: {}] Sending raw payload='{}' to topic='{}'",key, payload, topic);
		kafkaTemplate.sendRawMessage(topic, payload, key);
	}

	public void sendInMemoryMessage(String topic, String payload, String FPID, String key, String environment, String paymentType, Long qosMilliseconds) {
		LOG.info("[FPS][PmtId: {}] Sending payload='{}' to topic='{}'", key, payload, topic);
		kafkaTemplate.sendInMemoryMessage(topic, payload, key, environment, paymentType, FPID, qosMilliseconds);
	}
}
