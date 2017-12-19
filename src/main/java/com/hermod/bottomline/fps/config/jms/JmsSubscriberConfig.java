package com.hermod.bottomline.fps.config.jms;

import com.hermod.bottomline.fps.config.ComponentConfig;
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
	private MessageListener mqSIPListener;
    @Autowired
    private MessageListener mqASYNListener;

    /*
    @Autowired
    private MessageListener mqSIPOutboundRecvListener;
    */

    // INBOUND QUEUES TO RETRIEVE MESSAGES FROM BOTTOMLINE
	@Value("${wq.mq.queue.sip.inbound}")
	private String sipQueue;
    @Value("${wq.mq.queue.asyn.inbound}")
    private String asynQueue;

    // OUTBOUND QUEUES TO RETRIEVE RESP  MESSAGES FROM BOTTOMLINE
    @Value("${wq.mq.queue.sip.outbound.resp}")
    private String sipOutboundRecvQueue;


	@Value("${wq.mq.receive.num.max.consumers}")
	private Integer maxConcurrentConsumers;
	
	@Bean
    public DefaultMessageListenerContainer jmsSIPListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory)(applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(sipQueue);
        listenerContainer.setMessageListener(applicationContext.getBean("mqSIPListener"));
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

    @Bean
    public DefaultMessageListenerContainer jmsASYNCListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory)(applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(asynQueue);
        listenerContainer.setMessageListener(applicationContext.getBean("mqASYNCListener"));
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }


    @Bean
    public DefaultMessageListenerContainer jmsSIPOutboundListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory)(applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(sipOutboundRecvQueue);
        listenerContainer.setMessageListener(applicationContext.getBean("mqSIPOutboundRecvListener"));
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }

}
