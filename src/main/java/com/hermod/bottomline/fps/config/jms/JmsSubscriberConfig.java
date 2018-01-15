package com.hermod.bottomline.fps.config.jms;

import com.hermod.bottomline.fps.config.ComponentConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger LOG = LogManager.getLogger(JmsSubscriberConfig.class);
    @Autowired private ApplicationContext applicationContext;
	
	@Autowired
	private MessageListener mqSIPListener;
    @Autowired
    private MessageListener mqASYNListener;
    @Autowired
    private MessageListener mqPOOListener;
    @Autowired
    private MessageListener mqSTANDINListener;
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

    @Value("${connector.mq_primary}")
    private String bottomlineSite;

    @Value("${jms.mq.bottomline.environment.1}")
    private String bottomlineSite1Env;

    @Value("${jms.mq.bottomline.environment.2}")
    private String bottomlineSite2Env;
	
	@Bean
    public DefaultMessageListenerContainer jmsSIPListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
        listenerContainer.setDestinationName(sipQueue);
        listenerContainer.setMessageListener(mqSIPListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsASYNCListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
        listenerContainer.setDestinationName(asynQueue);
        listenerContainer.setMessageListener(mqASYNListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsSTANDINListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
        listenerContainer.setDestinationName(standinQueue);
        listenerContainer.setMessageListener(mqSTANDINListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsPOOListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
        listenerContainer.setDestinationName(pooQueue);
        listenerContainer.setMessageListener(mqPOOListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsSIPOutboundListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
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
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
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
        listenerContainer.setConnectionFactory(getConnectionFactoryByMQSite());
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(usmInboundQueue);
        listenerContainer.setMessageListener(mqUSMListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    private ConnectionFactory getConnectionFactoryByMQSite() {
        ConnectionFactory connectionFactory = null;
        if(bottomlineSite.equals(bottomlineSite1Env)) {
            connectionFactory = (ConnectionFactory) (applicationContext.getBean("mqQueueConnectionFactory"));
        }else{
            connectionFactory = (ConnectionFactory) (applicationContext.getBean("mqQueueConnectionSite2Factory"));
        }
        return connectionFactory;
    }

}
