package com.hermod.bottomline.fps.utils.properties;

import com.orwellg.umbrella.commons.utils.constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class DefaultPropertyValues {

	private final static String APP_DEFAULT_ORWELLG_ENTITY = Constants.IPAGOO_ENTITY;
	private final static String APP_DEFAULT_ORWELLG_BRAND  = Constants.IPAGOO_BRAND;

	private final static String MQ_DEFAULT_HOST  = "213.129.75.120";
	private final static Integer MQ_DEFAULT_PORT = 49178;
	private final static String MQ_DEFAULT_QUEUE_MANAGER = "BT.ORUN.UA.FPS.02";
	private final static String MQ_DEFAULT_CHANNEL  = "IPAGOO.CONNECT";
	private final static String MQ_DEFAULT_USERNAME = "ipagoo";
	private final static String MQ_DEFAULT_PASSWORD = "";

	/*
	private final static String MQ_DEFAULT_HOST  = "localhost";
	private final static Integer MQ_DEFAULT_PORT = 1414;
	private final static String MQ_DEFAULT_QUEUE_MANAGER = "QM3";
	private final static String MQ_DEFAULT_CHANNEL  = "DEV.APP.SVRCONN";
	private final static String MQ_DEFAULT_USERNAME = "root";
	private final static String MQ_DEFAULT_PASSWORD = "";
	 */

    private final static String MQ_DEFAULT_SECONDARY_HOST  = "80.169.11.104";
    private final static Integer MQ_DEFAULT_SECONDARY_PORT = 49178;
    private final static String MQ_DEFAULT_SECONDARY_QUEUE_MANAGER = "BT.ORUN.UA.FPS.02";
    private final static String MQ_DEFAULT_SECONDARY_CHANNEL  = "IPAGOO.CONNECT";
    private final static String MQ_DEFAULT_SECONDARY_USERNAME = "ipagoo";
    private final static String MQ_DEFAULT_SECONDARY_PASSWORD = "";

	private final static Long MQ_DEFAULT_RECEIVED_TIMEOUT = 2000L;
	private final static Integer MQ_DEFAULT_NUM_MAX_CONSUMERS = 1;
	private final static Integer MQ_DEFAULT_NUM_MAX_ATTEMPTS = 5;

	private final static String MQ_DEFAULT_TEST_QUEUE = "ORUN.TEST.INB.RECV.QR";

	private final static String MQ_DEFAULT_INBOUND_SIP_QUEUE  = "ORUN.HOST.SYNC.RECV.QL";
	private final static String MQ_DEFAULT_INBOUND_ASYN_QUEUE  = "ORUN.HOST.ASYN.RECV.QL";
	private final static String MQ_DEFAULT_INBOUND_POO_QUEUE  = "ORUN.HOST.POO.RECV.QL";
	private final static String MQ_DEFAULT_INBOUND_STANDIN_QUEUE  = "ORUN.HOST.STANDIN.RECV.QL";

	private final static String MQ_DEFAULT_INBOUND_USM_QUEUE  = "ORUN.HOST.USM.RECV.QL";
	private final static String MQ_DEFAULT_INBOUND_RESP_SIP_QUEUE = "ORUN.HOST.SYNC.RECV.RESP.QR";
	private final static String MQ_DEFAULT_INBOUND_RESP_ASYN_QUEUE = "ORUN.HOST.ASYN.RECV.RESP.QR";


	private final static String MQ_BOTTOMLINE1_ENVIRONMENT = "BOTTOMLINE1";
	private final static String MQ_BOTTOMLINE2_ENVIRONMENT = "BOTTOMLINE2";

	private final static String MQ_DEFAULT_OUTBOUND_SIP_QUEUE  = "ORUN.HOST.SYNC.SEND.QR";
	private final static String MQ_DEFAULT_OUTBOUND_SIP_RESP_QUEUE  = "ORUN.HOST.SYNC.SEND.RESP.QL";

	private final static String MQ_DEFAULT_OUTBOUND_ASYN_QUEUE  = "ORUN.HOST.ASYN.SEND.QR";
	private final static String MQ_DEFAULT_OUTBOUND_ASYN_RESP_QUEUE  = "ORUN.HOST.ASYN.SEND.RESP.QL";

	private final static String KAFKA_DEFAULT_BOOTSTRAP_SERVER = "confluent-node1:9092,confluent-node2:9092,confluent-node3:9092,confluent-node4:9092,confluent-node5:9092,confluent-node6:9092";

	private final static String KAFKA_DEFAULT_INBOUND_TOPIC_REPLYTO   = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.accounting.response.1";
	private final static String KAFKA_DEFAULT_INBOUND_TOPIC           = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.response.1";
	private final static String KAFKA_DEFAULT_INBOUND_RESPONSE_TOPIC  = "com.orwellg.yggdrasil.dsl.fps.inbound.response.1";
	private final static String KAFKA_DEFAULT_INBOUND_REJECT_TOPIC    = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.reject.1";
	private final static String KAFKA_DEFAULT_INBOUND_REQUEST_TOPIC   = "com.orwellg.yggdrasil.dsl.fps.inbound.payment.request.1";

	private final static String KAFKA_DEFAULT_OUTBOUND_RESPONSE_TOPIC = "com.orwellg.yggdrasil.dsl.fps.outbound.payment.response.1";
	private final static String KAFKA_DEFAULT_OUTBOUND_REQUEST_TOPIC  = "com.orwellg.hermod.bottomline.fps.outbound.payment.request.1";

	private final static String KAFKA_DEFAULT_USM_TOPIC_PARTITIONS  = "com.orwellg.yggdrasil.dsl.fps.inbound.usm.received.1";

	private final static String KAFKA_DEFAULT_INBOUND_REVERSAL_REQUEST_TOPIC = "com.orwellg.yggdrasil.dsl.fps.inbound.reversal.request.1";
	private final static String KAFKA_DEFAULT_INBOUND_REVERSAL_RESPONSE_TOPIC  = "com.orwellg.yggdrasil.dsl.fps.inbound.reversal.response.1";

	private final static String KAFKA_DEFAULT_MESSAGE_LOGGING_TOPIC   = "com.orwellg.logging.message";

	private final static Integer KAFKA_DEFAULT_OUTBOUND_TOPIC_PARTITIONS = 3;
	private final static String KAFKA_DEFAULT_CONSUMER_GROUP_ID = "hermod-bottomline-fps-inbound";
	private final static Integer KAFKA_DEFAULT_CONSUMER_THREADS = 3;
	private final static Long KAFKA_DEFAULT_CONSUMER_POLL_TIMEOUT = 3000L;
	private final static Integer INMEMORY_DEFAULT_CACHE_EXPIRINGMINUTES = 30;

	
	private final static Map<String, Object> defaultValues;
	
	static {
		
		defaultValues = new HashMap<>();
		
		defaultValues.put("entity.name", APP_DEFAULT_ORWELLG_ENTITY);
		defaultValues.put("brand.name", APP_DEFAULT_ORWELLG_BRAND);

		defaultValues.put("wq.mq.site1.host", MQ_DEFAULT_HOST);
		defaultValues.put("wq.mq.site1.port", MQ_DEFAULT_PORT);
		defaultValues.put("wq.mq.site1.queue.manager", MQ_DEFAULT_QUEUE_MANAGER);
		defaultValues.put("wq.mq.site1.channel", MQ_DEFAULT_CHANNEL);
		defaultValues.put("wq.mq.site1.username", MQ_DEFAULT_USERNAME);
		defaultValues.put("wq.mq.site1.password", MQ_DEFAULT_PASSWORD);

		defaultValues.put("wq.mq.site2.host", MQ_DEFAULT_SECONDARY_HOST);
		defaultValues.put("wq.mq.site2.port", MQ_DEFAULT_SECONDARY_PORT);
		defaultValues.put("wq.mq.site2.queue.manager", MQ_DEFAULT_SECONDARY_QUEUE_MANAGER);
		defaultValues.put("wq.mq.site2.channel", MQ_DEFAULT_SECONDARY_CHANNEL);
		defaultValues.put("wq.mq.site2.username", MQ_DEFAULT_SECONDARY_USERNAME);
		defaultValues.put("wq.mq.site2.password", MQ_DEFAULT_SECONDARY_PASSWORD);

		defaultValues.put("wq.mq.receive.timeout", MQ_DEFAULT_RECEIVED_TIMEOUT);

		// Test queues
		defaultValues.put("wq.mq.queue.test", MQ_DEFAULT_TEST_QUEUE);
		//Inbound queues BOTTOMLINE

		defaultValues.put("wq.mq.queue.sip.inbound", MQ_DEFAULT_INBOUND_SIP_QUEUE);
		defaultValues.put("wq.mq.queue.sip.inbound.resp", MQ_DEFAULT_INBOUND_RESP_SIP_QUEUE);
		defaultValues.put("wq.mq.queue.asyn.inbound", MQ_DEFAULT_INBOUND_ASYN_QUEUE);
		defaultValues.put("wq.mq.queue.poo.inbound", MQ_DEFAULT_INBOUND_POO_QUEUE);
		defaultValues.put("wq.mq.queue.standin.inbound", MQ_DEFAULT_INBOUND_STANDIN_QUEUE);
		defaultValues.put("wq.mq.queue.asyn.inbound.resp", MQ_DEFAULT_INBOUND_RESP_ASYN_QUEUE);
		defaultValues.put("wq.mq.queue.usm.inbound", MQ_DEFAULT_INBOUND_USM_QUEUE);

		//Outbound queues BOTTOMLINE
		defaultValues.put("wq.mq.queue.sip.outbound", MQ_DEFAULT_OUTBOUND_SIP_QUEUE);
		defaultValues.put("wq.mq.queue.sip.outbound.resp", MQ_DEFAULT_OUTBOUND_SIP_RESP_QUEUE);

		defaultValues.put("wq.mq.queue.asyn.outbound", MQ_DEFAULT_OUTBOUND_ASYN_QUEUE);
		defaultValues.put("wq.mq.queue.asyn.outbound.resp", MQ_DEFAULT_OUTBOUND_ASYN_RESP_QUEUE);


		defaultValues.put("wq.mq.receive.num.max.consumers", MQ_DEFAULT_NUM_MAX_CONSUMERS);
		defaultValues.put("wq.mq.num.max.attempts", MQ_DEFAULT_NUM_MAX_ATTEMPTS);
		defaultValues.put("jms.mq.bottomline.environment.1", MQ_BOTTOMLINE1_ENVIRONMENT);
		defaultValues.put("jms.mq.bottomline.environment.2", MQ_BOTTOMLINE2_ENVIRONMENT);

		defaultValues.put("kafka.bootstrap.host", KAFKA_DEFAULT_BOOTSTRAP_SERVER);
		// KAFKA TOPICS
		defaultValues.put("kafka.topic.inbound.request", KAFKA_DEFAULT_INBOUND_REQUEST_TOPIC);
		defaultValues.put("kafka.topic.inbound.response", KAFKA_DEFAULT_INBOUND_TOPIC);
		defaultValues.put("kafka.topic.inbound.response.replyTo", KAFKA_DEFAULT_INBOUND_TOPIC_REPLYTO);
		defaultValues.put("kafka.topic.inbound.reject", KAFKA_DEFAULT_INBOUND_REJECT_TOPIC);

		defaultValues.put("kafka.topic.inbound.response.payment", KAFKA_DEFAULT_INBOUND_RESPONSE_TOPIC);

		defaultValues.put("kafka.topic.outbound.request", KAFKA_DEFAULT_OUTBOUND_REQUEST_TOPIC);
		defaultValues.put("kafka.topic.outbound.response", KAFKA_DEFAULT_OUTBOUND_RESPONSE_TOPIC);
		defaultValues.put("kafka.topic.outbound.partitions", KAFKA_DEFAULT_OUTBOUND_TOPIC_PARTITIONS);

		defaultValues.put("kafka.topic.usm.message", KAFKA_DEFAULT_USM_TOPIC_PARTITIONS);
		defaultValues.put("kafka.topic.reversal.request", KAFKA_DEFAULT_INBOUND_REVERSAL_REQUEST_TOPIC);
		defaultValues.put("kafka.topic.reversal.response", KAFKA_DEFAULT_INBOUND_REVERSAL_RESPONSE_TOPIC);


		defaultValues.put("kafka.topic.fps.logging", KAFKA_DEFAULT_MESSAGE_LOGGING_TOPIC);

		defaultValues.put("kafka.consumer.group.id", KAFKA_DEFAULT_CONSUMER_GROUP_ID);
		defaultValues.put("kafka.consumer.threads.num", KAFKA_DEFAULT_CONSUMER_THREADS);
		defaultValues.put("kafka.consumer.poll.timeout", KAFKA_DEFAULT_CONSUMER_POLL_TIMEOUT);

		defaultValues.put("kafka.consumer.poll.timeout", KAFKA_DEFAULT_CONSUMER_POLL_TIMEOUT);
		defaultValues.put("inmemory.cache.expiringMinutes", INMEMORY_DEFAULT_CACHE_EXPIRINGMINUTES);
	}

	public static Map<String, Object> getDefaultValues() {
		return defaultValues;
	}

}
