package com.orwellg.hermod.bottomline.fps.config.jms;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import com.orwellg.hermod.bottomline.fps.config.ComponentConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;

@Configuration
@EnableTransactionManagement
public class JmsConnectionConfig extends ComponentConfig {

	private Logger LOG = LogManager.getLogger(JmsConnectionConfig.class);

	@Value("${wq.mq.site1.host}")
	private String host;
	@Value("${wq.mq.site1.port}")
	private Integer port;
	@Value("${wq.mq.site1.channel}")
	private String channel;
	@Value("${wq.mq.site1.queue.manager}")
	private String queueManager;
	@Value("${wq.mq.site1.username}")
	private String username;
	@Value("${wq.mq.site1.password}")
	private String password;
	@Value("${useSSL}")
	private Boolean useSSL;
	@Value("${SSLCipherSuite}")
	private String SSLCipherSuite;
	
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
	    mqQueueConnectionFactory.setHostName(host);
	    try {
	        mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

			if(useSSL) {
				mqQueueConnectionFactory.setSSLCipherSuite(SSLCipherSuite);
			}
	        // CCISD has to be the same within the Queue Manager, 1208 is UTF-8
	        mqQueueConnectionFactory.setCCSID(1208);
	        mqQueueConnectionFactory.setChannel(channel);
	        mqQueueConnectionFactory.setPort(port);
	        mqQueueConnectionFactory.setQueueManager(queueManager);
			mqQueueConnectionFactory.setPollingInterval(200);
	    } catch (Exception e) {
	    		LOG.error("Error creating the MQ Connection Factory. Message: {}", e.getMessage(), e);
	    }
	    
	    return mqQueueConnectionFactory;
	}
	
	@Bean
	public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
	    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	    userCredentialsConnectionFactoryAdapter.setUsername(username);
	    userCredentialsConnectionFactoryAdapter.setPassword(password);
	    userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
	    return userCredentialsConnectionFactoryAdapter;
	}
	
	@Bean
	//@Primary
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
	
}
