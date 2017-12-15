package com.hermod.bottomline.fps.utils.mq;

import com.hermod.bottomline.fps.listeners.BaseListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

@Component(value="messageTestQueueSender")
public class MessageTestQueueSender extends BaseListener {

    private static Logger LOG = LogManager.getLogger(MessageTestQueueSender.class);

    @Value("${wq.mq.queue.test}")
    private String testQueue;

    @Autowired
    private JmsOperations jmsOperations;

    @Autowired
    private Jaxb2Marshaller marshaller;

    public void sendMessage(String message, String key) {

        try {

            jmsOperations.send(testQueue, session -> {
                LOG.info("[FPS][PmtId: {}] Message to be sent to Test queue: {}",key, message);
                return session.createTextMessage(message);
            });

        } catch (Exception ex) {
            LOG.error("[FPS] Error sending message for testing. Error Message: {}", ex.getMessage());
        }

    }
}
