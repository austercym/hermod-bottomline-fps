package com.hermod.bottonline.fps.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaResponseConsumerConfig {

	@Value("${kafka.bootstrap.host}")
	private String bootstrap;
	@Value("${kafka.consumer.group.id}")
	private String groupId;
	@Value("${kafka.topic.inbound.response}")
	private String outboundTopic;
	@Value("${kafka.consumer.poll.timeout}")
	private Long consumerPoolTimeout;
	@Value("${kafka.consumer.threads.num}")
	private Integer numMaxThreads;
	
	@Autowired
	private MessageListener<?, ?> kafkaResponseListener;
	
	@Bean
	public Map<String, Object> consumerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
	    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
	    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
	    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	
	    return props;
	}

	@Bean
	public ConsumerFactory<?, ?> kafkaConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

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
