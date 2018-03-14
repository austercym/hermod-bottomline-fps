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
public class JmsSubscriberConfig extends ComponentConfig {
    @Autowired private ApplicationContext applicationContext;
	
	@Autowired
	private MessageListener mqSIPSite1Listener;
    @Autowired
    private MessageListener mqASYNSite1Listener;
    @Autowired
    private MessageListener mqPOOSite1Listener;
    @Autowired
    private MessageListener mqSTANDINSite1Listener;
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

    @Value("${connector.%id.mq_primary}")
    private String bottomlineSite;

    @Value("${jms.mq.bottomline.environment.1}")
    private String bottomlineSite1Env;

    @Value("${jms.mq.bottomline.environment.2}")
    private String bottomlineSite2Env;
	
	@Bean
    public DefaultMessageListenerContainer jmsSIPListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setDestinationName(sipQueue);
        listenerContainer.setMessageListener(mqSIPSite1Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setReceiveTimeout(200);
        listenerContainer.setRecoveryInterval(100);
        listenerContainer.setIdleConsumerLimit(5);
        listenerContainer.setIdleTaskExecutionLimit(10);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsASYNCListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setDestinationName(asynQueue);
        listenerContainer.setMessageListener(mqASYNSite1Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsSTANDINListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setDestinationName(standinQueue);
        listenerContainer.setMessageListener(mqSTANDINSite1Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsPOOListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setDestinationName(pooQueue);
        listenerContainer.setMessageListener(mqPOOSite1Listener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsSIPOutboundListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setDestinationName(sipOutboundRecvQueue);
        listenerContainer.setMessageListener(mqSIPOutboundRecvListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsAsynOutboundListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(asyncOutboundRecvQueue);
        listenerContainer.setMessageListener(mqAsynOutboundRecvListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsUSMListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(usmInboundQueue);
        listenerContainer.setMessageListener(mqUSMListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }
}
