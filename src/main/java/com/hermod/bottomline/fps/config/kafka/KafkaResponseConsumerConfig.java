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
public class KafkaResponseConsumerConfig extends KafkaConsumerConfig{

	@Value("${kafka.topic.inbound.response}")
	private String outboundTopic;
	@Value("${kafka.consumer.poll.timeout}")
	private Long consumerPoolTimeout;
	@Value("${kafka.consumer.threads.num}")
	private Integer numMaxThreads;
	
	@Autowired
	private MessageListener<?, ?> kafkaResponseListener;

	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaResponseListenerContainer() {
		
		ContainerProperties containerProperties = new ContainerProperties(outboundTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);
		
		ConcurrentMessageListenerContainer<?, ?> kafkaResponseListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerFactory(), 
				containerProperties
		);
		kafkaResponseListenerContainer.setConcurrency(numMaxThreads);
		kafkaResponseListenerContainer.setupMessageListener(kafkaResponseListener);
		
		return kafkaResponseListenerContainer;
	}

}
