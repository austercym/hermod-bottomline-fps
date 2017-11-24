package com.hermod.bottonline.fps.rest;

import com.hermod.bottonline.fps.listeners.MQSIPListener;
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

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIP(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        mqSIPListener.sendMessageToTopic(reader);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/send")
    public ResponseEntity<String> sendMQSIP(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        //TDOD Send message to MQ queue
        mqSIPListener.sendMessageToTopic(reader);
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }
}
