package com.hermod.bottomline.fps.services.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaSender {

	private static final Logger LOG = LogManager.getLogger(KafkaSender.class);

	@Autowired
	private KafkaWithHeadersTemplate<String, String> kafkaTemplate;

	public void send(String topic, String payload, String key, String replayTo, String environment, String paymentType) {
		LOG.info("[FPS][PmtId: {}] Sending payload='{}' to topic='{}'", key, payload, topic);
		kafkaTemplate.send(topic, payload, key, replayTo, environment, paymentType);
	}

	public void sendRawMessage(String topic, String payload, String key) {
		LOG.info("[FPS][PmtId: {} Sending payload='{}' to topic='{}'", payload, topic);
		kafkaTemplate.sendRawMessage(topic, payload, key);
	}
}
