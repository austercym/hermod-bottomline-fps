package com.orwellg.hermod.bottomline.fps.listeners.storage;


import com.google.gson.Gson;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.KafkaInboundListener;
import com.orwellg.hermod.bottomline.fps.services.transform.FPSTransform;
import com.orwellg.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.orwellg.hermod.bottomline.fps.storage.PaymentBean;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPaymentResponse;
import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundReversalResponse;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Map;


@Component(value = "kafkaResponseInMemoryListener")
public class KafkaResponseInMemoryListener extends KafkaInboundListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

    private static Logger LOG = LogManager.getLogger(KafkaResponseInMemoryListener.class);

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @Autowired
    protected Map<String, FPSTransform> transforms;

    @Override
    public void onMessage(ConsumerRecord<String, String> message) {

        Gson gson = new Gson();

        String key = message.key();
        String value = message.value();

        LOG.info("[FPS][PmtId: {}] Receiving a response to store inmemory", key);
        try {
            // Parse Event Message
            Event eventPayment = null;
            try {
                eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
            } catch (Exception ex) {
                LOG.error("[FPS][PmtId: {}] Error decoding event request for FPS outbound payment. Error Message: {}", key, ex.getMessage(), ex);
            }

            // Parse FPS Outbound Payment Request
            Headers headers = message.headers();
            Header header = headers.lastHeader(KafkaHeaders.FPS_PAYMENT_TYPE.getKafkaHeader());
            String paymentType = new String(header.value(), "UTF-8");
            Header headerFPID = headers.lastHeader(KafkaHeaders.FPS_PAYMENT_FPID.getKafkaHeader());
            String FPID = new String(headerFPID.value(), "UTF-8");
            Header headerEnvironment = headers.lastHeader(KafkaHeaders.FPS_SITE.getKafkaHeader());
            String environmentMQ = new String(headerEnvironment.value(), "UTF-8");

            String originalPaymentMessage = null;
            FPSOutboundPaymentResponse fpsPaymentResponse = null;
            boolean isRequest = false;
            try {
                fpsPaymentResponse = new Gson().fromJson(eventPayment.getEvent().getData(), FPSOutboundPaymentResponse.class);
                originalPaymentMessage = gson.toJson(fpsPaymentResponse.getOrgnlPaymentDocument());
                isRequest = true;
            } catch (Exception ex) {
                LOG.error("[FPS][PmtId: {}] Error parsing response for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
            }

            FPSOutboundReversalResponse fpsPaymentReversalResponse = null;
            if(!isRequest) {
                try {
                    fpsPaymentReversalResponse = new Gson().fromJson(eventPayment.getEvent().getData(), FPSOutboundReversalResponse.class);
                    originalPaymentMessage = gson.toJson(fpsPaymentReversalResponse.getRvsdDocument());
                } catch (Exception ex) {
                    LOG.error("[FPS][PmtId: {}] Error parsing response for FPS inbound reversal payment. Error Message: {}", key, ex.getMessage(), ex);
                }
            }

            // Generate Reversal Response
            FPSAvroMessage fpsPacs002Response = null;
            if(!isRequest) {
                fpsPacs002Response = generateFPSPacs002ReversalResponse(fpsPaymentReversalResponse);
            }else{
                fpsPacs002Response = generateFPSPacs002(fpsPaymentResponse.getOrgnlPaymentDocument(), fpsPaymentResponse.getPaymentId(),
                        fpsPaymentResponse.getStsRsn(), fpsPaymentResponse.getTxSts());
            }

            FPSTransform transform = transforms.get("transform_pacs_002_001");
            if (transform != null) {
                FPSMessage fpsMessage = transform.avro2fps(fpsPacs002Response);
                StringWriter rawMessage = transformResponseToString(fpsMessage);
                updatePaymentResponseInMemory(originalPaymentMessage, FPID, rawMessage.toString(), key, paymentType, environmentMQ);
            }

        } catch (Exception e) {
            throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
        }

    }

    protected PaymentBean updatePaymentResponseInMemory(String originalStr, String FPID,
                                                        String responseMessage, String paymentId,
                                                        String paymentType, String environmentMQ) {
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance(expiringMinutes);

        LOG.debug("[FPS][PmtId: {}] Storing response message to in-memory storage with FPID {}", paymentId, FPID);
        PaymentBean payment = storage.findPayment(FPID, originalStr);
        if (payment == null) {
            storage.storePayment(FPID, originalStr, paymentId, paymentType, environmentMQ);
        }
        payment = storage.completePaymentResponse(FPID, originalStr, responseMessage);

        return payment;
    }

}
