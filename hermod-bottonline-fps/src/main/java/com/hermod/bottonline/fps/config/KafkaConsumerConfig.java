package com.hermod.bottonline.fps.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import com.hermod.bottonline.fps.utils.factory.ConfigurationFactory;

@Configuration
//@EnableKafka
public class KafkaConsumerConfig {

	@Autowired
	private MessageListener<?, ?> kafkaListener;
	
	@Bean
	public Map<String, Object> consumerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
	    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigurationFactory.getConfigurationParams().getKafkaConfigurationParams().getBootstrap());
	    props.put(ConsumerConfig.GROUP_ID_CONFIG, ConfigurationFactory.getConfigurationParams().getKafkaConfigurationParams().getConsumerGroup());
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
	public ConcurrentMessageListenerContainer<?, ?> kafkaListenerContainer() {
		
		ContainerProperties containerProperties = new ContainerProperties(ConfigurationFactory.getConfigurationParams().getKafkaConfigurationParams().getOutboundTopic());
		containerProperties.setPollTimeout(ConfigurationFactory.getConfigurationParams().getKafkaConfigurationParams().getConsumerPollTimeout());
		
		ConcurrentMessageListenerContainer<?, ?> kafkaListenerContainer = new ConcurrentMessageListenerContainer<>(
				kafkaConsumerFactory(), 
				containerProperties
		);
		kafkaListenerContainer.setConcurrency(ConfigurationFactory.getConfigurationParams().getKafkaConfigurationParams().getNumConsumerThreads());
		kafkaListenerContainer.setupMessageListener(kafkaListener);
		
		return kafkaListenerContainer;
	}

}
