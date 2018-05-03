package com.orwellg.hermod.bottomline.fps.rest;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.BaseListener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.asyn.MQASYNSite1Listener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.asyn.MQASYNSite2Listener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.poo.MQPOOSite1Listener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.poo.MQPOOSite2Listener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.sync.MQSIPSite1Listener;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.sync.MQSIPSite2Listener;
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
import java.util.Date;


@RestController
public class SimulateSendEventToKafka {

    private static Logger LOG = LogManager.getLogger(SimulateSendEventToKafka.class);

    @Autowired

    MetricRegistry metricRegistry;

    @Autowired
    MessageTestQueueSender messageTestQueueSender;

    @Autowired
    @Qualifier("mqSIPSite1Listener")
    MQSIPSite1Listener mqSIPSite1Listener;

    @Autowired
    @Qualifier("mqSIPSite2Listener")
    MQSIPSite2Listener mqSIPSite2Listener;

    @Autowired
    @Qualifier("mqASYNSite1Listener")
    MQASYNSite1Listener mqASYNCSite1Listener;

    @Autowired
    @Qualifier("mqASYNSite2Listener")
    MQASYNSite2Listener mqASYNCSite2Listener;

    @Autowired
    @Qualifier("mqPOOSite1Listener")
    MQPOOSite1Listener mqPOOSite1Listener;

    @Autowired
    @Qualifier("mqPOOSite2Listener")
    MQPOOSite2Listener mqPOOSite2Listener;

    @Autowired
    MQUSMListener mqUSMListener;

    @Autowired
    MQSIPOutboundRecvListener mqOutboundListener;


    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @RequestMapping(method= RequestMethod.POST, value="/sip")
    public ResponseEntity<String> sendSIPSite1(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqSIPSite1Listener.sendMessageToTopic(writer, BaseListener.SIP, key, new Date().getTime());
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

    @RequestMapping(method= RequestMethod.POST, value="/sip2")
    public ResponseEntity<String> sendSIPSite2(@RequestBody String queueMessage,
                                               @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqSIPSite2Listener.sendMessageToTopic(writer, BaseListener.SIP, key, new Date().getTime());
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
    public ResponseEntity<String> sendMQAsyn(@RequestBody String queueMessage,
                                            @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqASYNCSite1Listener.sendMessageToTopic(writer, MQASYNSite1Listener.PAYMENT_TYPE, key, new Date().getTime());
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

    @RequestMapping(method= RequestMethod.POST, value="/asyn2")
    public ResponseEntity<String> sendMQAsynSite2(@RequestBody String queueMessage,
                                            @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqASYNCSite2Listener.sendMessageToTopic(writer, MQASYNSite2Listener.PAYMENT_TYPE, key, new Date().getTime());
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

    @RequestMapping(method= RequestMethod.POST, value="/poo")
    public ResponseEntity<String> sendPOO(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqPOOSite1Listener.sendMessageToTopic(writer, BaseListener.POO, key, new Date().getTime());
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

    @RequestMapping(method= RequestMethod.POST, value="/poo2")
    public ResponseEntity<String> sendPOOSite2(@RequestBody String queueMessage,
                                          @RequestHeader("x-process-id") String key) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqPOOSite2Listener.sendMessageToTopic(writer, BaseListener.POO, key, new Date().getTime());
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


    @RequestMapping(method= RequestMethod.POST, value="/messageResponse")
    public ResponseEntity<String> sendMQSIPOutboundResponse(@RequestBody String queueMessage) {
        Writer writer = null;
        try {
            writer = getStringWriter(queueMessage);
            mqOutboundListener.sendMessageToTopic(writer, BaseListener.SIP, null);
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

    @RequestMapping(method= RequestMethod.GET, value="/resetstorage")
    public ResponseEntity<String> cleanMemory() {
        InMemoryPaymentStorage inmemoryStorage = InMemoryPaymentStorage.getInstance(expiringMinutes);
        inmemoryStorage.clearStorage();
        return new ResponseEntity<>("Memory reset", HttpStatus.OK);
    }

    private Writer getStringWriter(@RequestBody String queueMessage) throws IOException {
        Reader reader = new StringReader(queueMessage);
        Writer writer = new StringWriter();
        IOUtils.copy(reader, writer);
        return writer;
    }
}
