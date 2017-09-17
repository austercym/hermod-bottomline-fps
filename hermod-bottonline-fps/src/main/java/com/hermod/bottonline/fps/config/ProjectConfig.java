package com.hermod.bottonline.fps.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

@Configuration
public class ProjectConfig {

	private static final Logger LOG = LogManager.getLogger(ProjectConfig.class);
	
	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaListenerContainer;
	
	@Autowired
	private DefaultMessageListenerContainer jmsListenerContainer;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    LOG.info("Aplication start. Starting containers....");
	    kafkaListenerContainer.start();
	    jmsListenerContainer.start();
	    LOG.info("Containers started");
	}
}
