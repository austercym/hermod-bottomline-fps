package com.hermod.bottomline.fps.rest;

import com.hermod.bottomline.fps.listeners.MQSIPListener;
import com.hermod.bottomline.fps.listeners.MQSOPListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Reader;
import java.io.StringReader;


@RestController
public class SimulateSendEventToKafka {

    @Autowired
    MQSIPListener mqSIPListener;

    @Autowired
    MQSOPListener mqSOPListener;

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIP(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        mqSIPListener.sendMessageToTopic(reader, MQSIPListener.PAYMENT_TYPE);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/sop")
    public ResponseEntity<String> sendMQSOP(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        mqSOPListener.sendMessageToTopic(reader, MQSOPListener.PAYMENT_TYPE);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }
}
