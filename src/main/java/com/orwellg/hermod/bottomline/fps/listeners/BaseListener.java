package com.orwellg.hermod.bottomline.fps.listeners;

import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.utils.QoSHeaders;
import com.orwellg.hermod.bottomline.fps.utils.QoSValidationExceotion;
import com.orwellg.hermod.bottomline.fps.utils.singletons.EventGenerator;
import com.orwellg.umbrella.avro.types.event.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;

import java.util.Date;
import java.util.Map;

public class BaseListener {

	private static Logger LOG = LogManager.getLogger(BaseListener.class);

	public static final String SIP = "SIP";
	public static final String SOP = "SOP";
	public static final String FDP = "FDP";
	public static final String CBP = "CBP";
	public static final String SRN = "SRN";
	public static final String RTN = "RTN";
	public static final String STANDIN = "STANDIN";
	public static final String POO = "POO";
	public static final String USM = "USM";


	@Autowired
	protected Map<String, FPSTransform> transforms;
	@Value("${wq.mq.num.max.attempts}")
	private int numMaxAttempts;

	@Value("${jms.mq.send.messages}")
	private Boolean sendMessageToBL;
	@Value("{entity.name}")
	protected String entity;
	@Value("${brand.name}")
	protected String brand;

	@Autowired
	private JmsOperations jmsOperations;

	@Autowired
	private JmsOperations jmsOperationsSite2;

	@Value("${jms.mq.bottomline.environment.1}")
	private String environmentMQSite1;

    @Value("${connector.emergency.log}")
    protected Boolean emergencyLog;

	protected FPSTransform getTransform(String packageName) {
		String beanKey = "transform_" + packageName.substring(packageName.lastIndexOf(".")+1);
		return transforms.getOrDefault(beanKey, null);
	}

	protected FPSTransform getTransformsUML(String className) {
		String beanKey = "transform_" + className.substring(className.lastIndexOf(".")+1);
		return transforms.getOrDefault(beanKey, null);
	}

	protected boolean sendToMQ(String key, String rawMessage, String queueToSend, String paymentType, String environmentMQ) {
		boolean messageSent = false;
		int numAttempts = numMaxAttempts;
		if(sendMessageToBL) {
			LOG.info("[FPS][PaymentType: {}][PmtId: {}] Sending message to queue {} to Bottomline {}. Attempts to try {}", paymentType, key, queueToSend, environmentMQ, numAttempts);
			while (!messageSent && numAttempts > 0) {
				try {
					LOG.info("[FPS][PaymentType: {}][PmtId: {}] Message to be sent to queue {} to Bottomline {}: {}", paymentType, key, queueToSend, environmentMQ, rawMessage);
					if (environmentMQ.equalsIgnoreCase(environmentMQSite1)) {
						jmsOperations.send(queueToSend, session -> session.createTextMessage(rawMessage));
					} else {
						jmsOperationsSite2.send(queueToSend, session -> session.createTextMessage(rawMessage));
					}
					messageSent = true;
				} catch (Exception ex) {
					LOG.error("[FPS] Error sending message to Bottomline. Error Message: {}", ex.getMessage());
					numAttempts--;
					LOG.info("[FPS][PaymentType: {}][PmtId: {}] Sending message to queue {} to Bottomline {}. Attempts to try {}", paymentType, key, queueToSend, environmentMQ, numAttempts);
				}
			}
			if (!messageSent) {
				LOG.error("[FPS][PmtId: {}] Error sending message to {} queue. Max number of attempts reached", key, queueToSend);
			}
		}else{
			LOG.debug("[FPS][PaymentType: {}][PmtId: {}] Message NOT SENT to queue {} to Bottomline {}", paymentType, key, queueToSend, environmentMQ);
		}
		return messageSent;
	}

	private Boolean validQoS(QoSHeaders qoSHeaders) {
		Long currentTimestamp = new Date().getTime();

		Boolean validQoS = Boolean.TRUE;

		Integer qosSLA = qoSHeaders.getQosSLA();
		Long qosTimestamp = qoSHeaders.getQosTimestamp();
		if(qosSLA != null && qosTimestamp != null) {
			LOG.info("[FPS] Checking QoS service, sla ={}, timestamp={}, current timestamp={}", qosSLA, qosTimestamp, currentTimestamp);
			if ((qosTimestamp + qosSLA) <= currentTimestamp) {
				validQoS = Boolean.FALSE;
			}
		}
		return validQoS;
	}

	protected boolean sendToMQ(String key, String rawMessage, String queueToSend, String paymentType, String environmentMQ, QoSHeaders qoSHeaders ) throws QoSValidationExceotion {
		boolean messageSent = false;
		int numAttempts = numMaxAttempts;
		if(sendMessageToBL) {
			LOG.info("[FPS][PaymentType: {}][PmtId: {}] Sending message to queue {} to Bottomline {}. Attempts to try {}", paymentType, key, queueToSend, environmentMQ, numAttempts);
			while (!messageSent && numAttempts > 0) {
				if(!validQoS(qoSHeaders)){
					throw new QoSValidationExceotion();
				}
				try {
					LOG.info("[FPS][PaymentType: {}][PmtId: {}] Message to be sent to queue {} to Bottomline {}: {}", paymentType, key, queueToSend, environmentMQ, rawMessage);
					if (environmentMQ.equalsIgnoreCase(environmentMQSite1)) {
						jmsOperations.send(queueToSend, session -> session.createTextMessage(rawMessage));
					} else {
						jmsOperationsSite2.send(queueToSend, session -> session.createTextMessage(rawMessage));
					}
					messageSent = true;
				} catch (Exception ex) {
					LOG.error("[FPS] Error sending message to Bottomline. Error Message: {}", ex.getMessage());
					numAttempts--;
					LOG.info("[FPS][PaymentType: {}][PmtId: {}] Sending message to queue {} to Bottomline {}. Attempts to try {}", paymentType, key, queueToSend, environmentMQ, numAttempts);
				}
			}
			if (!messageSent) {
				LOG.error("[FPS][PmtId: {}] Error sending message to {} queue. Max number of attempts reached", key, queueToSend);
			}
		}else{
			LOG.debug("[FPS][PaymentType: {}][PmtId: {}] Message NOT SENT to queue {} to Bottomline {}", paymentType, key, queueToSend, environmentMQ);
		}
		return messageSent;
	}

	protected Event getRawMessageEvent(String message, String uuid, String eventName) {
		Event event = EventGenerator.generateEvent(
				this.getClass().getName(),
				eventName,
				uuid,
				message,
				entity,
				brand
		);
		return event;
	}

}
