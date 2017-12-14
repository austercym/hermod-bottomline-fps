package com.hermod.bottomline.fps.utils.mq;

import com.hermod.bottomline.fps.listeners.BaseListener;
import com.hermod.bottomline.fps.services.transform.FPSTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.ibm.disthub2.client.Message;
import com.ibm.disthub2.impl.formats.OldEnvelop;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

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
            /*
            Source src = new StreamSource(new StringReader(message));
            final JAXBElement result = (JAXBElement) marshaller.unmarshal(src);
            FPSMessage fpsMessage = (FPSMessage) result.getValue();
            */
            jmsOperations.send(testQueue, session -> {
                LOG.info("[FPS][PmtId: {}] Message to be sent to Test queue: {}",key, message);
                return session.createTextMessage(message);
            });
            /*
            jmsOperations.convertAndSend(testQueue, message, messageToSend -> {
                messageToSend.setJMSType(MessageType.TEXT.toString());
                LOG.info("[FPS][PmtId: {}] Message of type {} to be sent to Test queue: {}",key, messageToSend.getJMSType(), messageToSend.toString());
                return messageToSend;
            });
            */

        } catch (Exception ex) {
            LOG.error("[FPS] Error sending message for testing. Error Message: {}", ex.getMessage());
        }

    }
}
