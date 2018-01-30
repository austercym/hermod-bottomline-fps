package com.orwellg.hermod.bottomline.fps.listeners.storage;


import com.orwellg.hermod.bottomline.fps.storage.InMemoryPaymentStorage;
import com.orwellg.hermod.bottomline.fps.storage.PaymentBean;
import com.orwellg.hermod.bottomline.fps.storage.PaymentStatus;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;


@Component(value = "kafkaRequestInMemoryListener")
public class KafkaRequestInMemoryListener  implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

    private static Logger LOG = LogManager.getLogger(KafkaRequestInMemoryListener.class);

    @Value("${inmemory.cache.expiringMinutes}")
    private int expiringMinutes;

    @Override
    public void onMessage(ConsumerRecord<String, String> message) {

        String key = message.key();
        String value = message.value();

        LOG.info("[FPS][PmtId: {}] Receiving a request to store inmemory", key);
        try {
            // Parse Event Message
            Event eventPayment = null;
            try {
                eventPayment = RawMessageUtils.decodeFromString(Event.SCHEMA$, value);
            } catch (Exception ex) {
                LOG.error("[FPS][PmtId: {}] Error decoding event request for FPS inbound payment. Error Message: {}", key, ex.getMessage(), ex);
            }

            // Parse FPS Outbound Payment Request
            Headers headers = message.headers();
            Header header = headers.lastHeader(KafkaHeaders.FPS_PAYMENT_TYPE.getKafkaHeader());
            String paymentType = new String(header.value(), "UTF-8");
            Header headerFPID = headers.lastHeader(KafkaHeaders.FPS_PAYMENT_FPID.getKafkaHeader());
            String FPID = new String(headerFPID.value(), "UTF-8");
            Header headerEnvironment = headers.lastHeader(KafkaHeaders.FPS_SITE.getKafkaHeader());
            String environmentMQ = new String(headerEnvironment.value(), "UTF-8");
            String originalPaymentMessage =eventPayment.getEvent().getData();

            storePaymentRequest(originalPaymentMessage,key, FPID, paymentType, environmentMQ);

        } catch (Exception e) {
            throw new MessageConversionException("Exception in message emission. Message: " + e.getMessage(), e);
        }

    }

    private PaymentBean storePaymentRequest(String message, String uuid, String FPID, String paymentType, String environmentMQ) {
        PaymentBean resendPreviousResponse = null;
        InMemoryPaymentStorage storage = InMemoryPaymentStorage.getInstance(expiringMinutes);
        PaymentBean payment = storage.findPayment(FPID, message);
        if (payment != null && payment.getStatus().equals(PaymentStatus.PROCESSED)){
            LOG.debug("FPS][PmtId: {}] Payment found in cache", uuid);
            resendPreviousResponse = payment;
        }else{
            LOG.debug("FPS][PmtId: {}] Payment not found in cache, storing into it", uuid);
            storage.storePayment(FPID, message, uuid, paymentType, environmentMQ);
        }
        return resendPreviousResponse;
    }

}
