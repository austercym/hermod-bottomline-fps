package com.hermod.bottonline.fps.bean.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.orwellg.umbrella.commons.beans.config.ZkConfigurationParams;
import com.orwellg.umbrella.commons.utils.config.ZookeeperUtils;
import com.orwellg.umbrella.commons.utils.constants.Constants;

public class ConfigurationParams extends ZkConfigurationParams {

	private final static Logger LOG = LogManager.getLogger(ConfigurationParams.class);
	
	public static final String DEFAULT_PROPERTIES_FILE = "hermod-bottonline-fps.properties";
	public static final String DEFAULT_SUB_BRANCH      = "/hermod/bottonline/fps"; 
	public static final String ZK_SUB_BRANCH_KEY       = "zookeeper.hermod.bottonline.fps.config.sub_branch";
	
	public static class MQConfigurationParams {
		
		private final static String DEFAULT_HOST  = "localhost";
		private final static Integer DEFAULT_PORT = 1414;
		private final static String DEFAULT_QUEUE_MANAGER = "FPS-QM";
		private final static String DEFAULT_CHANNEL  = Constants.EMPTY;
		private final static String DEFAULT_USERNAME = Constants.EMPTY;
		private final static String DEFAULT_PASSWORD = Constants.EMPTY;
		private final static Long DEFAULT_RECEIVED_TIMEOUT = 2000L;
		private final static Integer DEFAULT_NUM_MAX_CONSUMERS = 3;
		
		private final static String DEFAULT_INBOUND_QUEUE  = "Inbound";
		private final static String DEFAULT_OUTBOUND_QUEUE = "Outbound";
		
	    private DynamicStringProperty host;
	    private DynamicIntProperty port;
	    private DynamicStringProperty queueManager;
	    private DynamicStringProperty channel;
	    private DynamicStringProperty username;
	    private DynamicStringProperty password;
	    
	    private DynamicLongProperty receiveTimeout;
	    
	    private DynamicStringProperty inboundQueue;
	    private DynamicIntProperty numMaxConsumers;
	    private DynamicStringProperty outboundQueue;
	    
	  	public String getHost() { return (host != null) ? host.get() : DEFAULT_HOST; }
		public void setHost(DynamicStringProperty host) { this.host = host; }
		public Integer getPort() { return (port != null) ? port.get() : DEFAULT_PORT; }
		public void setPort(DynamicIntProperty port) { this.port = port; }
		public String getQueueManager() { return (queueManager != null) ? queueManager.get() : DEFAULT_QUEUE_MANAGER; }
		public void setQueueManager(DynamicStringProperty queueManager) { this.queueManager = queueManager; }
		public String getChannel() { return (channel != null) ? channel.get() : DEFAULT_CHANNEL; }
		public void setChannel(DynamicStringProperty channel) { this.channel = channel; }
		public String getUsername() { return (username != null) ? username.get() : DEFAULT_USERNAME; }
		public void setUsername(DynamicStringProperty username) { this.username = username; }
		public String getPassword() { return (password != null) ? password.get() : DEFAULT_PASSWORD; }
		public void setPassword(DynamicStringProperty password) { this.password = password; }
		public Long getReceiveTimeout() { return (receiveTimeout != null) ? receiveTimeout.get() : DEFAULT_RECEIVED_TIMEOUT; }
		public void setReceiveTimeout(DynamicLongProperty receiveTimeout) { this.receiveTimeout = receiveTimeout; }
		public String getInboundQueue() { return (inboundQueue != null) ? inboundQueue.get() : DEFAULT_INBOUND_QUEUE; }
		public void setInboundQueue(DynamicStringProperty inboundQueue) { this.inboundQueue = inboundQueue; }
		public String getOutboundQueue() { return (outboundQueue != null) ? outboundQueue.get() : DEFAULT_OUTBOUND_QUEUE; }
		public void setOutboundQueue(DynamicStringProperty outboundQueue) { this.outboundQueue = outboundQueue; }
		public Integer getNumMaxConsumers() {	return (numMaxConsumers != null) ? numMaxConsumers.get() : DEFAULT_NUM_MAX_CONSUMERS; }
		public void setNumMaxConsumers(DynamicIntProperty numMaxConsumers) { this.numMaxConsumers = numMaxConsumers; }
		
	}
	
	public static class KafkaConfigurationParams {
		
		private final static String DEFAULT_BOOTSTRAP_SERVER = "localhost:9092";
		private final static String DEFAULT_INBOUND_TOPIC  = "com.orwellg.fps.request";
		private final static String DEFAULT_OUTBOUND_TOPIC = "com.orwellg.fps.response";
		
		private DynamicStringProperty bootstrap;
	    private DynamicStringProperty inboundTopic;
	    private DynamicStringProperty outboundTopic;
	    
		public String getBootstrap() { return (bootstrap != null) ? bootstrap.get() : DEFAULT_BOOTSTRAP_SERVER; }
		public void setBootstrap(DynamicStringProperty bootstrap) { this.bootstrap = bootstrap; }
		public String getInboundTopic() { return (inboundTopic != null) ? inboundTopic.get() : DEFAULT_INBOUND_TOPIC; }
		public void setInboundTopic(DynamicStringProperty inboundTopic) { this.inboundTopic = inboundTopic; }
		public String getOutboundTopic() { return (outboundTopic != null) ? outboundTopic.get() : DEFAULT_OUTBOUND_TOPIC; }
		public void setOutboundTopic(DynamicStringProperty outboundTopic) { this.outboundTopic = outboundTopic; }
	   
	}
	
	private MQConfigurationParams mqConfigurationParams;
	private KafkaConfigurationParams kafkaConfigurationParams;
	
	public MQConfigurationParams getMQConfigurationParams() { return mqConfigurationParams; }
	public KafkaConfigurationParams getKafkaConfigurationParams() { return kafkaConfigurationParams; }
	
	@Override
	protected void loadParameters() {
		
		DynamicPropertyFactory dynamicPropertyFactory = null;
		try {
			dynamicPropertyFactory = ZookeeperUtils.getDynamicPropertyFactory();
		} catch (Exception e) {
			LOG.error("Error when try get the dynamic property factory from Zookeeper. Message: {}",  e.getMessage(), e);
		}

		mqConfigurationParams = new MQConfigurationParams();
		mqConfigurationParams.setHost(dynamicPropertyFactory.getStringProperty("wq.mq.host", MQConfigurationParams.DEFAULT_HOST));
		mqConfigurationParams.setPort(dynamicPropertyFactory.getIntProperty("wq.mq.port", MQConfigurationParams.DEFAULT_PORT));
		mqConfigurationParams.setQueueManager(dynamicPropertyFactory.getStringProperty("wq.mq.queue.manager", MQConfigurationParams.DEFAULT_QUEUE_MANAGER));
		mqConfigurationParams.setChannel(dynamicPropertyFactory.getStringProperty("wq.mq.channel", MQConfigurationParams.DEFAULT_CHANNEL));
		mqConfigurationParams.setUsername(dynamicPropertyFactory.getStringProperty("wq.mq.username", MQConfigurationParams.DEFAULT_USERNAME));
		mqConfigurationParams.setPassword(dynamicPropertyFactory.getStringProperty("wq.mq.password", MQConfigurationParams.DEFAULT_PASSWORD));
		mqConfigurationParams.setReceiveTimeout(dynamicPropertyFactory.getLongProperty("wq.mq.receive.timeout", MQConfigurationParams.DEFAULT_RECEIVED_TIMEOUT));
		mqConfigurationParams.setInboundQueue(dynamicPropertyFactory.getStringProperty("wq.mq.queue.inbound", MQConfigurationParams.DEFAULT_INBOUND_QUEUE));
		mqConfigurationParams.setOutboundQueue(dynamicPropertyFactory.getStringProperty("wq.mq.queue.outbound", MQConfigurationParams.DEFAULT_OUTBOUND_QUEUE));
		mqConfigurationParams.setNumMaxConsumers(dynamicPropertyFactory.getIntProperty("wq.mq.receive.num.max.consumers", MQConfigurationParams.DEFAULT_NUM_MAX_CONSUMERS));
	
		kafkaConfigurationParams = new KafkaConfigurationParams();
		kafkaConfigurationParams.setBootstrap(dynamicPropertyFactory.getStringProperty("kafka.bootstrap.host", KafkaConfigurationParams.DEFAULT_BOOTSTRAP_SERVER));
		kafkaConfigurationParams.setInboundTopic(dynamicPropertyFactory.getStringProperty("kafka.topic.inbound", KafkaConfigurationParams.DEFAULT_INBOUND_TOPIC));
		kafkaConfigurationParams.setOutboundTopic(dynamicPropertyFactory.getStringProperty("kafka.topic.outbound", KafkaConfigurationParams.DEFAULT_OUTBOUND_TOPIC));
	}

	public ConfigurationParams() {
		super();
		super.setPropertiesFile(DEFAULT_PROPERTIES_FILE);
		super.setApplicationRootConfig(ZK_SUB_BRANCH_KEY, DEFAULT_SUB_BRANCH);
	}
	
}
