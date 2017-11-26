package com.hermod.bottomline.fps.config.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaRejectConsumerConfig extends KafkaConsumerConfig{

	@Value("${kafka.topic.inbound.reject}")
	private String outboundTopic;
	@Value("${kafka.consumer.poll.timeout}")
	private Long consumerPoolTimeout;
	@Value("${kafka.consumer.threads.num}")
	private Integer numMaxThreads;
	
	@Autowired
	private MessageListener<?, ?> kafkaRejectListener;

	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaRejectListenerContainer() {
		
		ContainerProperties containerProperties = new ContainerProperties(outboundTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);
		
		ConcurrentMessageListenerContainer<?, ?> kafkaRejectListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerFactory(), 
				containerProperties
		);
		kafkaRejectListenerContainer.setConcurrency(numMaxThreads);
		kafkaRejectListenerContainer.setupMessageListener(kafkaRejectListener);
		
		return kafkaRejectListenerContainer;
	}

}
