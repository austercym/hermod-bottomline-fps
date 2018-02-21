package com.orwellg.hermod.bottomline.fps.listeners;

import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;

import java.util.Map;

public class BaseListener {

	private static Logger LOG = LogManager.getLogger(BaseListener.class);

	@Autowired
	protected Map<String, FPSTransform> transforms;
	@Value("${wq.mq.num.max.attempts}")
	private int numMaxAttempts;

	@Value("${jms.mq.send.messages}")
	private Boolean sendMessageToBL;

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
					LOG.error("[FPS] Error sending message for testing. Error Message: {}", ex.getMessage());
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

}