package com.hermod.bottomline.fps.config.jms;

import com.hermod.bottomline.fps.config.ComponentConfig;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;

@Configuration
@EnableTransactionManagement
public class JmsConnectionSite2Config extends ComponentConfig {

	private Logger LOG = LogManager.getLogger(JmsConnectionSite2Config.class);

	@Value("${wq.mq.site2.host}")
	private String host;
	@Value("${wq.mq.site2.port}")
	private Integer port;
	@Value("${wq.mq.site2.channel}")
	private String channel;
	@Value("${wq.mq.site2.queue.manager}")
	private String queueManager;
	@Value("${wq.mq.site2.username}")
	private String username;
	@Value("${wq.mq.site2.password}")
	private String password;
	
	@Bean
    public ConnectionFactory connectionSite2Factory() {
        return mqQueueConnectionSite2Factory();
    }

    @Bean
    public QueueConnectionFactory queueConnectionSite2Factory() {
        return mqQueueConnectionSite2Factory();
    }
    
	@Bean
	public MQQueueConnectionFactory mqQueueConnectionSite2Factory() {
		
	    MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	    mqQueueConnectionFactory.setHostName(host);
	    try {
	        mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
	        // CCISD has to be the same within the Queue Manager, 1208 is UTF-8
	        mqQueueConnectionFactory.setCCSID(1208);
	        mqQueueConnectionFactory.setChannel(channel);
	        mqQueueConnectionFactory.setPort(port);
	        mqQueueConnectionFactory.setQueueManager(queueManager);
	    } catch (Exception e) {
	    		LOG.error("Error creating the MQ Connection Site 2 Factory. Message: {}", e.getMessage(), e);
	    }
	    
	    return mqQueueConnectionFactory;
	}
	
	@Bean
	public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionSite2FactoryAdapter(MQQueueConnectionFactory mqQueueConnectionSite2Factory) {
	    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	    userCredentialsConnectionFactoryAdapter.setUsername(username);
	    userCredentialsConnectionFactoryAdapter.setPassword(password);
	    userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionSite2Factory);
	    return userCredentialsConnectionFactoryAdapter;
	}
	
	@Bean
	public CachingConnectionFactory cachingConnectionSite2Factory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionSite2FactoryAdapter) {
	    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
	    cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionSite2FactoryAdapter);
	    cachingConnectionFactory.setSessionCacheSize(500);
	    cachingConnectionFactory.setReconnectOnException(true);
	    cachingConnectionFactory.setCacheConsumers(true);
	    return cachingConnectionFactory;
	}
	
	@Bean
	public PlatformTransactionManager jmsTransactionSite2Manager(CachingConnectionFactory cachingConnectionSite2Factory) {
	    JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
	    jmsTransactionManager.setConnectionFactory(cachingConnectionSite2Factory);
	    return jmsTransactionManager;
	}
	
}
