package com.orwellg.hermod.bottomline.fps.rest;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQASYNListener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQPOOListener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQSIPListener;
import com.orwellg.hermod.bottomline.fps.listeners.outbound.MQSIPOutboundRecvListener;
import com.orwellg.hermod.bottomline.fps.listeners.usm.MQUSMListener;
import com.orwellg.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.orwellg.hermod.bottomline.fps.utils.mq.MessageTestQueueSender;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;


@RestController
public class SimulateSendEventToKafka {

    private static Logger LOG = LogManager.getLogger(SimulateSendEventToKafka.class);

    @Autowired

    MetricRegistry metricRegistry;

    @Autowired
    MessageTestQueueSender messageTestQueueSender;

    @Autowired
    @Qualifier("mqSIPListener")
    MQSIPListener mqSIPListener;

    @Autowired
    MQSIPListener mqSIPSite2Listener;

    @Autowired
    MQASYNListener mqASYNCListener;

    @Autowired
    MQPOOListener mqPOOListener;

    @Autowired
    MQUSMListener mqUSMListener;

    @Autowired
    MQSIPOutboundRecvListener mqOutboundListener;


    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIP(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqSIPListener.sendMessageToTopic(writer, MQSIPListener.PAYMENT_TYPE, key);
            return new ResponseEntity<>("Message sent ", HttpStatus.OK);
        }catch(IOException e){
            LOG.error("Error processing message: {}", e.getMessage());
            return new ResponseEntity<>("Message not sent ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e) {
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }

    private Writer getStringWriter(@RequestBody String queueMessage) throws IOException {
        Reader reader = new StringReader(queueMessage);
        Writer writer = new StringWriter();
        IOUtils.copy(reader, writer);
        return writer;
    }

    @RequestMapping(method= RequestMethod.POST, value="/poo")
    public ResponseEntity<String> sendPOO(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqPOOListener.sendMessageToTopic(writer, MQSIPListener.PAYMENT_TYPE, key);
            return new ResponseEntity<>("Message sent ", HttpStatus.OK);
        }catch(IOException e){
            LOG.error("Error processing message: {}", e.getMessage());
            return new ResponseEntity<>("Message not sent ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e) {
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }

    @RequestMapping(method= RequestMethod.POST, value="/asyn")
    public ResponseEntity<String> sendMQSOP(@RequestBody String queueMessage,
                                            @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqASYNCListener.sendMessageToTopic(writer, MQASYNListener.PAYMENT_TYPE, key);
            return new ResponseEntity<>("Message sent ", HttpStatus.OK);
        }catch(IOException e){
            LOG.error("Error processing message: {}", e.getMessage());
            return new ResponseEntity<>("Message not sent ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e) {
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }

    @RequestMapping(method= RequestMethod.GET, value="/resetstorage")
    public ResponseEntity<String> cleanMemory() {
        InMemoryPaymentStorage inmemoryStorage = InMemoryPaymentStorage.getInstance(expiringMinutes);
        inmemoryStorage.clearStorage();
        return new ResponseEntity<>("Memory reset", HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value="/messageResponse")
    public ResponseEntity<String> sendMQSIPOutboundResponse(@RequestBody String queueMessage) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqOutboundListener.sendMessageToTopic(writer, MQSIPOutboundRecvListener.PAYMENT_TYPE, null);
            return new ResponseEntity<>("Message sent ", HttpStatus.OK);
        }catch(IOException e){
            LOG.error("Error processing message: {}", e.getMessage());
            return new ResponseEntity<>("Message not sent ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e) {
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }

    @RequestMapping(method= RequestMethod.POST, value="/usm")
    public ResponseEntity<String> sendUSM(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try{
            writer= getStringWriter(queueMessage);
            mqUSMListener.sendMessageToTopic(writer, key);
            return new ResponseEntity<>("Message sent ", HttpStatus.OK);
        }catch(IOException e){
            LOG.error("Error processing message: {}", e.getMessage());
            return new ResponseEntity<>("Message not sent ", HttpStatus.INTERNAL_SERVER_ERROR);
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }catch (Exception e) {
                LOG.error("[FPS] Error closing streams resources. Message: {}", e.getMessage());
            }
        }
    }

    @RequestMapping(method= RequestMethod.POST, value="/testMessage")
    public ResponseEntity<String> sendTestMessage(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        messageTestQueueSender.sendMessage(queueMessage, key);
        return new ResponseEntity<>("Message sent ", HttpStatus.OK);
    }
}
