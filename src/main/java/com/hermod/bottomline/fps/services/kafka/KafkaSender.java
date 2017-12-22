package com.hermod.bottomline.fps.services.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaSender {

	private static final Logger LOG = LogManager.getLogger(KafkaSender.class);

	@Autowired
	private KafkaWithHeadersTemplate<String, String> kafkaTemplate;

	public void send(String topic, String payload, String key, String replayTo, String environment, String paymentType,
					 boolean isPOO) {
		LOG.info("[FPS][PmtId: {}] Sending payload='{}' to topic='{}'", key, payload, topic);
		kafkaTemplate.send(topic, payload, key, replayTo, environment, paymentType, isPOO);
	}

	public void sendRawMessage(String topic, String payload, String key) {
		LOG.info("[FPS][PmtId: {}] Sending raw payload='{}' to topic='{}'",key, payload, topic);
		kafkaTemplate.sendRawMessage(topic, payload, key);
	}
}
