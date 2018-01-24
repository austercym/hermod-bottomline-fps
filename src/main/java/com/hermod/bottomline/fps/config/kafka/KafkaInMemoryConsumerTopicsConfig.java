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
public class KafkaInMemoryConsumerTopicsConfig extends KafkaConsumerInMemoryConfig{

	@Value("${kafka.topic.cache.request}")
	private String requestPaymentInMemoryTopic;

	@Value("${kafka.topic.cache.response}")
	private String responsePaymentInMemoryTopic;

	@Value("${kafka.consumer.poll.timeout}")
	private Long consumerPoolTimeout;
	@Value("${kafka.consumer.threads.num}")
	private Integer numMaxThreads;
	
	@Autowired
	private MessageListener<?, ?> kafkaResponseInMemoryListener;

	@Autowired
	private MessageListener<?, ?> kafkaRequestInMemoryListener;


	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaResponseInMemoryListenerContainer() {
		
		ContainerProperties containerProperties = new ContainerProperties(responsePaymentInMemoryTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);
		
		ConcurrentMessageListenerContainer<?, ?> kafkaResponseInMemoryListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerInMemoryFactory(),
				containerProperties
		);
		kafkaResponseInMemoryListenerContainer.setConcurrency(numMaxThreads);
		kafkaResponseInMemoryListenerContainer.setupMessageListener(kafkaResponseInMemoryListener);
		
		return kafkaResponseInMemoryListenerContainer;
	}

	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaRequestInMemoryListenerContainer() {

		ContainerProperties containerProperties = new ContainerProperties(requestPaymentInMemoryTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);

		ConcurrentMessageListenerContainer<?, ?> kafkaRequestInMemoryListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerInMemoryFactory(),
				containerProperties
		);
		kafkaRequestInMemoryListenerContainer.setConcurrency(numMaxThreads);
		kafkaRequestInMemoryListenerContainer.setupMessageListener(kafkaRequestInMemoryListener);

		return kafkaRequestInMemoryListenerContainer;
	}

}
