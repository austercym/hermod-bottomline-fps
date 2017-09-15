package com.hermod.bottonline.fps.managers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.hermod.bottonline.fps.managers.ManagerHelloService;

@Component(value="managerHelloService")
public class ManagerHelloServiceImpl implements ManagerHelloService {
	
	private String topic;
	
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	@Autowired
	public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public String getTopic() {
		return topic;
	}

	@Value("${test.topic.name}")
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void sendHelloServiceEvent(String name) {
		
		StringBuffer event = new StringBuffer();
		event.append("Hello send for the user ").append(name);
		
		kafkaTemplate.send(topic, "1", event.toString());
		kafkaTemplate.flush();
		if (event != null && event.length() > 0) { event.delete(0, event.length()); }
	}

}
