package com.hermod.bottomline.fps.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;

@Configuration
public class ProjectConfig extends ComponentConfig {

	private static final Logger LOG = LogManager.getLogger(ProjectConfig.class);

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaResponseInboundListenerContainer;
	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaRequestOutboundListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSOPListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPOutboundListenerContainer;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    LOG.info("Connector to Bottomline start. Starting containers....");
		kafkaResponseInboundListenerContainer.start();
		kafkaRequestOutboundListenerContainer.start();
		jmsSIPListenerContainer.start();
		jmsSOPListenerContainer.start();
		jmsSIPOutboundListenerContainer.start();

	    LOG.info("Connector started");
	}
}
