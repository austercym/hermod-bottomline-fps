package com.hermod.bottonline.fps.config.jms;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import com.hermod.bottonline.fps.config.ComponentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableJms
public class JmsSubscriberConfig extends ComponentConfig {
    @Autowired private ApplicationContext applicationContext;
	
	@Autowired
	private MessageListener mqSIPListener;
    @Autowired
    private MessageListener mqSOPListener;
	
	@Value("${wq.mq.queue.sip.inbound}")
	private String sipQueue;
    @Value("${wq.mq.queue.sop.inbound}")
    private String sopQueue;
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
    public DefaultMessageListenerContainer jmsSOPListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory((ConnectionFactory)(applicationContext.getBean("mqQueueConnectionFactory")));
        listenerContainer.setSessionTransacted(true);
        listenerContainer.setDestinationName(sopQueue);
        listenerContainer.setMessageListener(applicationContext.getBean("mqSOPListener"));
        listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
        listenerContainer.setSessionTransacted(true);

        return listenerContainer;
    }
}
