package com.orwellg.hermod.bottomline.fps.config.kafka;

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
	private String inboundTopic;

	@Value("${kafka.topic.reversal.response}")
	private String inboundReversalTopic;

	@Value("${kafka.topic.outbound.request}")
	private String outboundTopic;


	@Value("${kafka.consumer.poll.timeout}")
	private Long consumerPoolTimeout;
	@Value("${kafka.consumer.threads.num}")
	private Integer numMaxThreads;
	
	@Autowired
	private MessageListener<?, ?> kafkaResponseInboundListener;

	@Autowired
	private MessageListener<?, ?> kafkaResponseReversalInboundListener;

	@Autowired
	private MessageListener<?, ?> kafkaRequestOutboundListener;

	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaResponseInboundListenerContainer() {
		
		ContainerProperties containerProperties = new ContainerProperties(inboundTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);
		
		ConcurrentMessageListenerContainer<?, ?> kafkaResponseInboundListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerFactory(), 
				containerProperties
		);
		kafkaResponseInboundListenerContainer.setConcurrency(numMaxThreads);
		kafkaResponseInboundListenerContainer.setupMessageListener(kafkaResponseInboundListener);
		
		return kafkaResponseInboundListenerContainer;
	}

	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaResponseReversalInboundListenerContainer() {

		ContainerProperties containerProperties = new ContainerProperties(inboundReversalTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);

		ConcurrentMessageListenerContainer<?, ?> kafkaResponseReversalInboundListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerFactory(),
				containerProperties
		);
		kafkaResponseReversalInboundListenerContainer.setConcurrency(numMaxThreads);
		kafkaResponseReversalInboundListenerContainer.setupMessageListener(kafkaResponseReversalInboundListener);

		return kafkaResponseReversalInboundListenerContainer;
	}

	@Bean
	public ConcurrentMessageListenerContainer<?, ?> kafkaRequestOutboundListenerContainer() {

		ContainerProperties containerProperties = new ContainerProperties(outboundTopic);
		containerProperties.setPollTimeout(consumerPoolTimeout);

		ConcurrentMessageListenerContainer<?, ?> kafkaRequestOutboundListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerFactory(),
				containerProperties
		);
		kafkaRequestOutboundListenerContainer.setConcurrency(numMaxThreads);
		kafkaRequestOutboundListenerContainer.setupMessageListener(kafkaRequestOutboundListener);

		return kafkaRequestOutboundListenerContainer;
	}

}
