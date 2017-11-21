package com.hermod.bottonline.fps.rest;

import com.hermod.bottonline.fps.listeners.MQListener;
import com.hermod.bottonline.fps.services.transform.helper.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Reader;
import java.io.StringReader;


@RestController
public class SimulateSendEventToKafka {

    @Autowired
    MQListener mqListener;

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIP(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        try {
            mqListener.sendMessageToTopic(reader);
            return new ResponseEntity<>("Message sent", HttpStatus.OK);
        } catch (ConversionException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed message sent: "+e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(method= RequestMethod.POST, value="/send")
    public ResponseEntity<String> sendMQSIP(@RequestBody String queueMessage) {
        Reader reader = new StringReader(queueMessage);
        try {
            //TDOD Send message to MQ queue
            mqListener.sendMessageToTopic(reader);
            return new ResponseEntity<>("Message sent", HttpStatus.OK);
        } catch (ConversionException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed message sent: "+e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
