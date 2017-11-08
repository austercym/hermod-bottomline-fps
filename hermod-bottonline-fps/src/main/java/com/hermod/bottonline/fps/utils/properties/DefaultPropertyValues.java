package com.hermod.bottonline.fps.utils.properties;

import java.util.HashMap;
import java.util.Map;

import com.orwellg.umbrella.commons.utils.constants.Constants;

public class DefaultPropertyValues {

	private final static String APP_DEFAULT_ORWELLG_ENTITY = Constants.IPAGOO_ENTITY;
	private final static String APP_DEFAULT_ORWELLG_BRAND  = Constants.IPAGOO_BRAND;
	
	private final static String MQ_DEFAULT_HOST  = "192.168.2.101";
	private final static Integer MQ_DEFAULT_PORT = 2414;
	private final static String MQ_DEFAULT_QUEUE_MANAGER = "FPS_TEST";
	private final static String MQ_DEFAULT_CHANNEL  = "FPS_CHANNEL";
	private final static String MQ_DEFAULT_USERNAME = "Particular";
	private final static String MQ_DEFAULT_PASSWORD = "347.m0n0";
	private final static Long MQ_DEFAULT_RECEIVED_TIMEOUT = 2000L;
	private final static Integer MQ_DEFAULT_NUM_MAX_CONSUMERS = 3;
	private final static String MQ_DEFAULT_INBOUND_QUEUE  = "FPS_INBOUND";
	private final static String MQ_DEFAULT_OUTBOUND_QUEUE = "FPS_OUTBOUND";
	
	private final static String KAFKA_DEFAULT_BOOTSTRAP_SERVER = "localhost:9092,localhost:10092";
	private final static String KAFKA_DEFAULT_INBOUND_TOPIC  = "com.orwellg.fps.request";
	private final static String KAFKA_DEFAULT_OUTBOUND_TOPIC = "com.orwellg.fps.response";
	private final static Integer KAFKA_DEFAULT_OUTBOUND_TOPIC_PARTITIONS = 3;
	private final static String KAFKA_DEFAULT_CONSUMER_GROUP_ID = "fpsConsumerGroup";
	private final static Integer KAFKA_DEFAULT_CONSUMER_THREADS = 3;
	private final static Long KAFKA_DEFAULT_CONSUMER_POLL_TIMEOUT = 3000L;

	
	private final static Map<String, Object> defaultValues;
	
	static {
		
		defaultValues = new HashMap<>();
		
		defaultValues.put("entity.name", APP_DEFAULT_ORWELLG_ENTITY);
		defaultValues.put("brand.name", APP_DEFAULT_ORWELLG_BRAND);

		defaultValues.put("wq.mq.host", MQ_DEFAULT_HOST);
		defaultValues.put("wq.mq.port", MQ_DEFAULT_PORT);
		defaultValues.put("wq.mq.queue.manager", MQ_DEFAULT_QUEUE_MANAGER);
		defaultValues.put("wq.mq.channel", MQ_DEFAULT_CHANNEL);
		defaultValues.put("wq.mq.username", MQ_DEFAULT_USERNAME);
		defaultValues.put("wq.mq.password", MQ_DEFAULT_PASSWORD);
		defaultValues.put("wq.mq.receive.timeout", MQ_DEFAULT_RECEIVED_TIMEOUT);
		defaultValues.put("wq.mq.queue.inbound", MQ_DEFAULT_INBOUND_QUEUE);
		defaultValues.put("wq.mq.queue.outbound", MQ_DEFAULT_OUTBOUND_QUEUE);
		defaultValues.put("wq.mq.receive.num.max.consumers", MQ_DEFAULT_NUM_MAX_CONSUMERS);
	
		defaultValues.put("kafka.bootstrap.host", KAFKA_DEFAULT_BOOTSTRAP_SERVER);
		defaultValues.put("kafka.topic.inbound", KAFKA_DEFAULT_INBOUND_TOPIC);
		defaultValues.put("kafka.topic.outbound", KAFKA_DEFAULT_OUTBOUND_TOPIC);
		defaultValues.put("kafka.topic.outbound.partitions", KAFKA_DEFAULT_OUTBOUND_TOPIC_PARTITIONS);
		defaultValues.put("kafka.consumer.group.id", KAFKA_DEFAULT_CONSUMER_GROUP_ID);
		defaultValues.put("kafka.consumer.threads.num", KAFKA_DEFAULT_CONSUMER_THREADS);
		defaultValues.put("kafka.consumer.poll.timeout", KAFKA_DEFAULT_CONSUMER_POLL_TIMEOUT);
	}

	public static Map<String, Object> getDefaultValues() {
		return defaultValues;
	}

}