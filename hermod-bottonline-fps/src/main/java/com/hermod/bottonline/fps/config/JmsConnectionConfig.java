package com.hermod.bottonline.fps.config;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hermod.bottonline.fps.utils.factory.ConfigurationFactory;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
@EnableTransactionManagement
public class JmsConnectionConfig extends ComponentConfig {

	private Logger LOG = LogManager.getLogger(JmsConnectionConfig.class);
	
	@Bean
    public ConnectionFactory connectionFactory() {
        return mqQueueConnectionFactory();
    }

    @Bean
    public QueueConnectionFactory queueConnectionFactory() {
        return mqQueueConnectionFactory();
    }
    
	@Bean
	public MQQueueConnectionFactory mqQueueConnectionFactory() {
		
	    MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	    mqQueueConnectionFactory.setHostName(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getHost());
	    try {
	        mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
	        // CCISD has to be the same within the Queue Manager, 1208 is UTF-8
	        mqQueueConnectionFactory.setCCSID(1208);
	        mqQueueConnectionFactory.setChannel(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getChannel());
	        mqQueueConnectionFactory.setPort(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getPort());
	        mqQueueConnectionFactory.setQueueManager(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getQueueManager());
	    } catch (Exception e) {
	    		LOG.error("Error creating the MQ Connection Factory. Message: {}", e.getMessage(), e);
	    }
	    
	    return mqQueueConnectionFactory;
	}
	
	@Bean
	public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
	    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	    userCredentialsConnectionFactoryAdapter.setUsername(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getUsername());
	    userCredentialsConnectionFactoryAdapter.setPassword(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getPassword());
	    userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
	    return userCredentialsConnectionFactoryAdapter;
	}
	
	@Bean
	@Primary
	public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
	    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
	    cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
	    cachingConnectionFactory.setSessionCacheSize(500);
	    cachingConnectionFactory.setReconnectOnException(true);
	    cachingConnectionFactory.setCacheConsumers(true);
	    return cachingConnectionFactory;
	}
	
	@Bean
	public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
	    JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
	    jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
	    return jmsTransactionManager;
	}
	
	@Bean
	public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setClassesToBeBound(
	    		iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_004_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_007_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_009_001.Document.class
	    	);
	    return marshaller;
	}
	
	@Bean
	public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) {
		
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
	    jmsTemplate.setReceiveTimeout(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getReceiveTimeout());
	    
	    // Creating and asing the message converter for jmsTemplate
	    MarshallingMessageConverter converter = new MarshallingMessageConverter();
	    	converter.setMarshaller(marshaller());
	    	converter.setUnmarshaller(marshaller());
	    	// set this converter on the implicit Spring JMS template
	    	jmsTemplate.setMessageConverter(converter);
	    	    
	    return jmsTemplate;
	}
}
