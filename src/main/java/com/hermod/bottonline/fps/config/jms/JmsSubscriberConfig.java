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

    public final static String SPLIT_CHARACTER_QUEUE_NAMES = ";";

    @Autowired private ApplicationContext applicationContext;
	
	@Autowired
	private MessageListener mqListener;
	
	@Value("${wq.mq.queue.inbound}")
	private String destinationsName;
	@Value("${wq.mq.receive.num.max.consumers}")
	private Integer maxConcurrentConsumers;
	
	@Bean
    public DefaultMessageListenerContainer jmsListenerContainer(ConnectionFactory connectionFactory)
    {
        ArrayList<DefaultMessageListenerContainer> listListeners = new ArrayList<>();
        String[] destinationQueues = destinationsName.split(SPLIT_CHARACTER_QUEUE_NAMES);


        //for (int i = 0; i < destinationQueues.length; i++) {
            DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
            listenerContainer.setConnectionFactory((ConnectionFactory)(applicationContext.getBean("mqQueueConnectionFactory")));
            listenerContainer.setSessionTransacted(true);
            listenerContainer.setDestinationName(destinationQueues[0]);
            listenerContainer.setMessageListener(applicationContext.getBean("mqListener"));
            listenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
            listenerContainer.setSessionTransacted(true);
            listListeners.add(listenerContainer);
        //}


        return listenerContainer;
    }
}
