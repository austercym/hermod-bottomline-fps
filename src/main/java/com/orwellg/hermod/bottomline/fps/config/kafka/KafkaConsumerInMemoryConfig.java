package com.orwellg.hermod.bottomline.fps.config.kafka;

import com.orwellg.umbrella.commons.config.KafkaConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerInMemoryConfig {

    private static Logger LOG = LogManager.getLogger(KafkaConsumerInMemoryConfig.class);

    @Value("${kafka.bootstrap.host}")
    private String bootstrap;
    @Value("${connector.%id.groupId}")
    private String groupId;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public Map<String, Object> consumerInMemoryConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

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
    public ConsumerFactory<?, ?> kafkaConsumerInMemoryFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerInMemoryConfigs());
    }
}
