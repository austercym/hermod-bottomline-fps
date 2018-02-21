package com.orwellg.hermod.bottomline.fps.config.kafka;

import com.orwellg.hermod.bottomline.fps.config.ComponentConfig;
import com.orwellg.hermod.bottomline.fps.services.kafka.KafkaSender;
import com.orwellg.umbrella.commons.config.KafkaConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig extends ComponentConfig {

	private static Logger LOG = LogManager.getLogger(KafkaProducerConfig.class);

	@Value("${kafka.bootstrap.host}")
	private String bootstrap;
    @Autowired private ApplicationContext applicationContext;
	
	@Bean
	public Map<String, Object> producerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaConfig kafkaConfigLocal = (KafkaConfig) (applicationContext.getBean("kafkaConnectorConfig"));

	    if(kafkaConfigLocal != null) {
            if(kafkaConfigLocal.getSslParams() != null) {
				if(StringUtils.isNotEmpty(kafkaConfigLocal.getSslParams().getSecurityProtocol())) {
					props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaConfigLocal.getSslParams().getSecurityProtocol());
				}
				if(StringUtils.isNotEmpty(kafkaConfigLocal.getSslParams().getSslTruststorePath())) {
					props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, kafkaConfigLocal.getSslParams().getSslTruststorePath());
				}
				if(StringUtils.isNotEmpty(kafkaConfigLocal.getSslParams().getSslTruststorePassword())) {
					props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, kafkaConfigLocal.getSslParams().getSslTruststorePassword());
				}
				if(StringUtils.isNotEmpty(kafkaConfigLocal.getSslParams().getSslKeystorePath())) {
					props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, kafkaConfigLocal.getSslParams().getSslKeystorePath());
				}
				if(StringUtils.isNotEmpty(kafkaConfigLocal.getSslParams().getSslKeystorePassword())) {
					props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, kafkaConfigLocal.getSslParams().getSslKeystorePassword());
				}
            }else {
                LOG.error("[FPS] Error loading params ssl from KafkaConfig");
            }
		}else{
			LOG.error("[FPS] Error loading params from KafkaConfig");
		}
	    return props;
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public KafkaSender kafkaSender() {
		return new KafkaSender();
	}
}
