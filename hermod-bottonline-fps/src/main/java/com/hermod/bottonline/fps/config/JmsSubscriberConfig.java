package com.hermod.bottonline.fps.config;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@EnableJms
public class JmsSubscriberConfig extends ComponentConfig {
	
	@Autowired
	private MessageListener mqListener;
	
	@Value("${wq.mq.queue.inbound}")
	private String destinationName;
	@Value("${wq.mq.receive.num.max.consumers}")
	private Integer maxConcurrentConsumers;
	
	@Bean
    public DefaultMessageListenerContainer jmsListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setDestinationName(destinationName);
        listenerContainer.setMessageListener(mqListener);
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);
        
        return listenerContainer;
    }
}
