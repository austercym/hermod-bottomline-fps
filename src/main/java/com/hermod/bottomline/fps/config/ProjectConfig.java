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
	private AbstractMessageListenerContainer<?,?> kafkaResponseReversalInboundListenerContainer;

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaRequestOutboundListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsASYNCListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsPOOListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPOutboundListenerContainer;
	@Autowired
	private DefaultMessageListenerContainer jmsAsynOutboundListenerContainer;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    LOG.info("Connector to Bottomline start. Starting containers....");
		kafkaResponseInboundListenerContainer.start();
		kafkaRequestOutboundListenerContainer.start();
		kafkaResponseReversalInboundListenerContainer.start();
		jmsSIPListenerContainer.start();
		jmsASYNCListenerContainer.start();
		jmsSIPOutboundListenerContainer.start();
		jmsAsynOutboundListenerContainer.start();
		jmsPOOListenerContainer.start();

	    LOG.info("Connector started");
	}
}
