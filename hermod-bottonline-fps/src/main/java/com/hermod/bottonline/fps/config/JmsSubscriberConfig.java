package com.hermod.bottonline.fps.config;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.hermod.bottonline.fps.utils.factory.ConfigurationFactory;

@Configuration
@EnableJms
public class JmsSubscriberConfig extends ComponentConfig {
	
	@Autowired
	private MessageListener mqListener;
	
	@Bean
    public DefaultMessageListenerContainer jmsListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setDestinationName(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getInboundQueue());
        listenerContainer.setMessageListener(mqListener);
        listenerContainer.setMaxConcurrentConsumers(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getNumMaxConsumers());
        listenerContainer.setSessionTransacted(true);
        
        return listenerContainer;
    }
}
