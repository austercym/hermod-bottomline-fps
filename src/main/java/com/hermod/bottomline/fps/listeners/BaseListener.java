package com.hermod.bottomline.fps.listeners;

import com.hermod.bottomline.fps.services.transform.FPSTransform;
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

	@Autowired
	private JmsOperations jmsOperations;

	protected FPSTransform getTransform(String packageName) {
		String beanKey = "transform_" + packageName.substring(packageName.lastIndexOf(".")+1);
		return transforms.getOrDefault(beanKey, null);
	}

	protected FPSTransform getTransformsUML(String className) {
		String beanKey = "transform_" + className.substring(className.lastIndexOf(".")+1);
		return transforms.getOrDefault(beanKey, null);
	}

	protected void sendToMQ(String key, String rawMessage, String queueToSend, String paymentType) {
		boolean messageSent = false;
		while (!messageSent && numMaxAttempts>0) {
			try {
				LOG.info("[FPS][PaymentType: {}][PmtId: {}] Message to be sent to queue {} to Bottomline: {}", paymentType, key, queueToSend, rawMessage);
				jmsOperations.send(queueToSend, session -> session.createTextMessage(rawMessage));
				messageSent = true;
			} catch (Exception ex) {
				LOG.error("[FPS] Error sending message for testing. Error Message: {}", ex.getMessage());
				numMaxAttempts--;
			}
		}
	}

}
