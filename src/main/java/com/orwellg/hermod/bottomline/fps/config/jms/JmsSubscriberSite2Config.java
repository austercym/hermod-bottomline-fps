package com.orwellg.hermod.bottomline.fps.config.jms;

import com.orwellg.hermod.bottomline.fps.config.ComponentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

@Configuration
@EnableJms
public class JmsSubscriberSite2Config extends ComponentConfig {

    @Autowired private ApplicationContext applicationContext;
	
	@Autowired
	private MessageListener mqSIPSite2Listener;
    @Autowired
    private MessageListener mqASYNSite2Listener;
    @Autowired
    private MessageListener mqPOOSite2Listener;
    @Autowired
    private MessageListener mqSTANDINSite2Listener;
    @Autowired
    private MessageListener mqSIPOutboundRecvListener;
    @Autowired
    private MessageListener mqAsynOutboundRecvListener;
    @Autowired
    private MessageListener mqUSMListener;

    // INBOUND QUEUES TO RETRIEVE MESSAGES FROM BOTTOMLINE
	@Value("${wq.mq.queue.sip.inbound}")
	private String sipQueue;
    @Value("${wq.mq.queue.asyn.inbound}")
    private String asynQueue;
    @Value("${wq.mq.queue.poo.inbound}")
    private String pooQueue;
    @Value("${wq.mq.queue.standin.inbound}")
    private String standinQueue;
    @Value("${wq.mq.queue.usm.inbound}")
    private String usmInboundQueue;

    // OUTBOUND QUEUES TO RETRIEVE RESP  MESSAGES FROM BOTTOMLINE
    @Value("${wq.mq.queue.sip.outbound.resp}")
    private String sipOutboundRecvQueue;

    @Value("${wq.mq.queue.asyn.outbound.resp}")
    private String asyncOutboundRecvQueue;

	@Value("${wq.mq.receive.num.max.consumers}")
	private Integer maxConcurrentConsumers;

    @Value("${jms.mq.bottomline.environment.2}")
    private String bottomlineSite2Env;
	
	@Bean
    public DefaultMessageListenerContainer jmsSIPListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setDestinationName(sipQueue);
        listenerContainer.setMessageListener(mqSIPSite2Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setReceiveTimeout(200);
        listenerContainer.setRecoveryInterval(100);
        listenerContainer.setIdleConsumerLimit(5);
        listenerContainer.setIdleTaskExecutionLimit(10);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsASYNCListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setDestinationName(asynQueue);
        listenerContainer.setMessageListener(mqASYNSite2Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsSTANDINListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setDestinationName(standinQueue);
        listenerContainer.setMessageListener(mqSTANDINSite2Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsPOOListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setDestinationName(pooQueue);
        listenerContainer.setMessageListener(mqPOOSite2Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsSIPOutboundListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setDestinationName(sipOutboundRecvQueue);
        listenerContainer.setMessageListener(mqSIPOutboundRecvListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsAsynOutboundListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(asyncOutboundRecvQueue);
        listenerContainer.setMessageListener(mqAsynOutboundRecvListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsUSMListenerSite2Container(ConnectionFactory connectionSite2Factory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(usmInboundQueue);
        listenerContainer.setMessageListener(mqUSMListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

}
