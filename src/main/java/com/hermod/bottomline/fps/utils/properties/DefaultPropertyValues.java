package com.hermod.bottomline.fps.utils.properties;

import java.util.HashMap;
import java.util.Map;

import com.orwellg.umbrella.commons.utils.constants.Constants;

public class DefaultPropertyValues {

	private final static String APP_DEFAULT_ORWELLG_ENTITY = Constants.IPAGOO_ENTITY;
	private final static String APP_DEFAULT_ORWELLG_BRAND  = Constants.IPAGOO_BRAND;
	
	private final static String MQ_DEFAULT_HOST  = "localhost";
	private final static Integer MQ_DEFAULT_PORT = 1414;
	private final static String MQ_DEFAULT_QUEUE_MANAGER = "QM1";
	private final static String MQ_DEFAULT_CHANNEL  = "DEV.APP.SVRCONN";
	private final static String MQ_DEFAULT_USERNAME = "root";
	private final static String MQ_DEFAULT_PASSWORD = "";
	private final static Long MQ_DEFAULT_RECEIVED_TIMEOUT = 2000L;
	private final static Integer MQ_DEFAULT_NUM_MAX_CONSUMERS = 1;
	private final static String MQ_DEFAULT_INBOUND_SIP_QUEUE  = "IPAGOO.HOST.SIP.RECV.QR";
	private final static String MQ_DEFAULT_INBOUND_SOP_QUEUE  = "IPAGOO.HOST.SOP.RECV.QR";
	private final static String MQ_DEFAULT_INBOUND_OTH_QUEUE  = "IPAGOO.HOST.OTH.RECV.QR";
	private final static String MQ_DEFAULT_INBOUND_USM_QUEUE  = "IPAGOO.HOST.USM.RECV.QR";
	private final static String MQ_DEFAULT_INBOUND_RESP_SIP_QUEUE = "IPAGOO.HOST.SIP.RECV.RESP.QR";
	private final static String MQ_DEFAULT_INBOUND_RESP_SOP_QUEUE = "IPAGOO.HOST.SOP.RECV.RESP.QR";
	private final static String MQ_DEFAULT_INBOUND_RESP_OTH_QUEUE = "IPAGOO.HOST.OTH.RECV.RESP.QR";
	private final static String MQ_BOTTOMLINE1_ENVIRONMENT = "BOTTOMLINE1";
	private final static String MQ_BOTTOMLINE2_ENVIRONMENT = "BOTTOMLINE2";

	private final static String KAFKA_DEFAULT_BOOTSTRAP_SERVER = "confluent-node1:9092,confluent-node2:9092,confluent-node3:9092,confluent-node4:9092,confluent-node5:9092,confluent-node6:9092";
	//private final static String KAFKA_DEFAULT_BOOTSTRAP_SERVER = "localhost:9092";
	private final static String KAFKA_DEFAULT_INBOUND_TOPIC  = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.response.1";
	private final static String KAFKA_DEFAULT_INBOUND_REJECT_TOPIC = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.reject.1";
	private final static String KAFKA_DEFAULT_OUTBOUND_TOPIC = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.request.1";
	private final static String KAFKA_DEFAULT_OUTBOUND_REJECT_TOPIC = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.reject.1";
	private final static String KAFKA_DEFAULT_MESSAGE_LOGGING_TOPIC = "com.orwellg.logging.message";
	private final static Integer KAFKA_DEFAULT_OUTBOUND_TOPIC_PARTITIONS = 3;
	private final static String KAFKA_DEFAULT_CONSUMER_GROUP_ID = "hermod-bottomline-fps-inbound";
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
		defaultValues.put("wq.mq.queue.sip.inbound", MQ_DEFAULT_INBOUND_SIP_QUEUE);
		defaultValues.put("wq.mq.queue.sop.inbound", MQ_DEFAULT_INBOUND_SOP_QUEUE);
		defaultValues.put("wq.mq.queue.oth.inbound", MQ_DEFAULT_INBOUND_OTH_QUEUE);
		defaultValues.put("wq.mq.queue.usm.inbound", MQ_DEFAULT_INBOUND_USM_QUEUE);
		defaultValues.put("wq.mq.queue.sip.inbound.resp", MQ_DEFAULT_INBOUND_RESP_SIP_QUEUE);
		defaultValues.put("wq.mq.queue.sop.inbound.resp", MQ_DEFAULT_INBOUND_RESP_SOP_QUEUE);
		defaultValues.put("wq.mq.queue.oth.inbound.resp", MQ_DEFAULT_INBOUND_RESP_OTH_QUEUE);

		defaultValues.put("wq.mq.receive.num.max.consumers", MQ_DEFAULT_NUM_MAX_CONSUMERS);
		defaultValues.put("jms.mq.bottomline.environment.1", MQ_BOTTOMLINE1_ENVIRONMENT);
		defaultValues.put("jms.mq.bottomline.environment.2", MQ_BOTTOMLINE2_ENVIRONMENT);

		defaultValues.put("kafka.bootstrap.host", KAFKA_DEFAULT_BOOTSTRAP_SERVER);
		defaultValues.put("kafka.topic.inbound.response", KAFKA_DEFAULT_INBOUND_TOPIC);
		defaultValues.put("kafka.topic.inbound.reject", KAFKA_DEFAULT_INBOUND_REJECT_TOPIC);
		defaultValues.put("kafka.topic.outbound.request", KAFKA_DEFAULT_OUTBOUND_TOPIC);
		defaultValues.put("kafka.topic.outbound.reject", KAFKA_DEFAULT_OUTBOUND_REJECT_TOPIC);
		defaultValues.put("kafka.topic.outbound.partitions", KAFKA_DEFAULT_OUTBOUND_TOPIC_PARTITIONS);
		defaultValues.put("kafka.topic.fps.logging", KAFKA_DEFAULT_MESSAGE_LOGGING_TOPIC);
		defaultValues.put("kafka.consumer.group.id", KAFKA_DEFAULT_CONSUMER_GROUP_ID);
		defaultValues.put("kafka.consumer.threads.num", KAFKA_DEFAULT_CONSUMER_THREADS);
		defaultValues.put("kafka.consumer.poll.timeout", KAFKA_DEFAULT_CONSUMER_POLL_TIMEOUT);
	}

	public static Map<String, Object> getDefaultValues() {
		return defaultValues;
	}

}
