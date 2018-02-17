package com.orwellg.hermod.bottomline.fps.config;

import com.orwellg.hermod.bottomline.fps.utils.generators.SchemeValidatorBean;
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
	private AbstractMessageListenerContainer<?,?> kafkaRequestInMemoryListenerContainer;

	@Autowired
	private AbstractMessageListenerContainer<?,?> kafkaResponseInMemoryListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsASYNCListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSTANDINListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsPOOListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsUSMListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPOutboundListenerContainer;
	@Autowired
	private DefaultMessageListenerContainer jmsAsynOutboundListenerContainer;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsASYNCListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsSTANDINListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsPOOListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsUSMListenerSite2Container;

	@Autowired
	private DefaultMessageListenerContainer jmsSIPOutboundListenerSite2Container;
	@Autowired
	private DefaultMessageListenerContainer jmsAsynOutboundListenerSite2Container;

	@EventListener(ApplicationReadyEvent.class)
	public void startListeners() {
	    LOG.info("Connector to Bottomline start. Starting containers....");
		try{
			SchemeValidatorBean.getInstance();
		}catch(Exception e){
			LOG.error("[FPS] Error creating scheme validators to validate payment messages");
		}
		jmsSIPListenerContainer.start();
		jmsASYNCListenerContainer.start();
		jmsSTANDINListenerContainer.start();
		jmsSIPOutboundListenerContainer.start();
		jmsAsynOutboundListenerContainer.start();
		jmsPOOListenerContainer.start();
		jmsUSMListenerContainer.start();

		jmsSIPListenerSite2Container.start();
		jmsASYNCListenerSite2Container.start();
		jmsSTANDINListenerSite2Container.start();
		jmsSIPOutboundListenerSite2Container.start();
		jmsAsynOutboundListenerSite2Container.start();
		jmsPOOListenerSite2Container.start();
		jmsUSMListenerSite2Container.start();

		kafkaResponseInboundListenerContainer.start();
		kafkaRequestOutboundListenerContainer.start();
		kafkaResponseReversalInboundListenerContainer.start();
		kafkaRequestInMemoryListenerContainer.start();
		kafkaResponseInMemoryListenerContainer.start();

	    LOG.info("Connector started");
	}
}
