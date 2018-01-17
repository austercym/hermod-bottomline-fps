package com.hermod.bottomline.fps.rest;

import com.hermod.bottomline.fps.listeners.inbound.MQASYNListener;
import com.hermod.bottomline.fps.listeners.inbound.MQPOOListener;
import com.hermod.bottomline.fps.listeners.inbound.MQSIPListener;
import com.hermod.bottomline.fps.listeners.outbound.MQSIPOutboundRecvListener;
import com.hermod.bottomline.fps.listeners.usm.MQUSMListener;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.hermod.bottomline.fps.utils.mq.MessageTestQueueSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Reader;
import java.io.StringReader;


@RestController
public class SimulateSendEventToKafka {

    private static Logger LOG = LogManager.getLogger(SimulateSendEventToKafka.class);

    @Autowired
    MQSIPListener mqSIPListener;

    @Autowired
    MQASYNListener mqASYNCListener;

    @Autowired
    MQPOOListener mqPOOListener;

    @Autowired
    MQUSMListener mqUSMListener;

    @Autowired
    MQSIPOutboundRecvListener mqOutboundListener;

    @Autowired
    MessageTestQueueSender messageTestQueueSender;

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIP(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Reader reader = new StringReader(queueMessage);
        mqSIPListener.sendMessageToTopic(reader, MQSIPListener.PAYMENT_TYPE, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/poo")
    public ResponseEntity<String> sendPOO(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Reader reader = new StringReader(queueMessage);
        mqPOOListener.sendMessageToTopic(reader, MQSIPListener.PAYMENT_TYPE, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/asyn")
    public ResponseEntity<String> sendMQSOP(@RequestBody String queueMessage,
                                            @RequestHeader("x-process-id") String key) {
        Reader reader = new StringReader(queueMessage);
        mqASYNCListener.sendMessageToTopic(reader, MQASYNListener.PAYMENT_TYPE, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.GET, value="/resetstorage")
    public ResponseEntity<String> cleanMemory() {
        InMemoryPaymentStorage inmemoryStorage = InMemoryPaymentStorage.getInstance(expiringMinutes);
        inmemoryStorage.cleanStorage();
        return new ResponseEntity<>("Memory reset", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/messageResponse")
    public ResponseEntity<String> sendMQSIPOutboundResponse(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        mqOutboundListener.sendMessageToTopic(reader, MQSIPOutboundRecvListener.PAYMENT_TYPE, null);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/usm")
    public ResponseEntity<String> sendUSM(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Reader reader = new StringReader(queueMessage);
        mqUSMListener.sendMessageToTopic(reader, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/testMessage")
    public ResponseEntity<String> sendTestMessage(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        messageTestQueueSender.sendMessage(queueMessage, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }
}
