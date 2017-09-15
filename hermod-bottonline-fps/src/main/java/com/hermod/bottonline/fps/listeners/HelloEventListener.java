package com.hermod.bottonline.fps.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class HelloEventListener extends EventListener {

	@Override
	@KafkaListener(topics="${test.topic.name}")
	public void receive(String content) {
		System.out.println("Say hello event receive. The content of event is " + content + ".");
	}
}
