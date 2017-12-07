package com.hermod.bottomline.fps.rest;

import com.hermod.bottomline.fps.listeners.inbound.MQSIPListener;
import com.hermod.bottomline.fps.listeners.inbound.MQSOPListener;
import com.hermod.bottomline.fps.listeners.outbound.MQOutboundListener;
import com.hermod.bottomline.fps.listeners.outbound.MQSIPOutboundRecvListener;
import com.hermod.bottomline.fps.storage.InMemoryPaymentStorage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Reader;
import java.io.StringReader;


@RestController
public class SimulateSendEventToKafka {

    private static Logger LOG = LogManager.getLogger(SimulateSendEventToKafka.class);

    @Autowired
    MQSIPListener mqSIPListener;

    @Autowired
    MQSOPListener mqSOPListener;

    @Autowired
    MQSIPOutboundRecvListener mqOutboundListener;

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIP(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Reader reader = new StringReader(queueMessage);
        mqSIPListener.sendMessageToTopic(reader, MQSIPListener.PAYMENT_TYPE, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/sop")
    public ResponseEntity<String> sendMQSOP(@RequestBody String queueMessage,
                                            @RequestHeader("x-process-id") String key) {
        Reader reader = new StringReader(queueMessage);
        mqSOPListener.sendMessageToTopic(reader, MQSOPListener.PAYMENT_TYPE, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.GET, value="/resetstorage")
    public ResponseEntity<String> cleanMemory() {
        InMemoryPaymentStorage inmemoryStorage = InMemoryPaymentStorage.getInstance();
        inmemoryStorage.cleanStorage();
        return new ResponseEntity<>("Memory reset", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/messageResponse")
    public ResponseEntity<String> sendMQSIPOutboundResponse(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        mqOutboundListener.sendMessageToTopic(reader, MQSIPOutboundRecvListener.PAYMENT_TYPE, null);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }
}
