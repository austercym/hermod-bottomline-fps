package com.orwellg.hermod.bottomline.fps.config;


import com.orwellg.umbrella.commons.config.KafkaConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaBLConfig extends ComponentConfig {

	private Logger LOG = LogManager.getLogger(KafkaBLConfig.class);

	@Value("${propertyFileName}")
	private String propertyFileName;


	@Bean
	public KafkaConfig kafkaConnectorConfig() {
		KafkaConfig kafkaConfig = new KafkaConfig(propertyFileName);
		try {
			kafkaConfig.start();
		} catch (Exception e) {
			LOG.error("Error creating kafkaConfig {}", e.getMessage(), e);
		}

		return kafkaConfig;
	}

	
}
