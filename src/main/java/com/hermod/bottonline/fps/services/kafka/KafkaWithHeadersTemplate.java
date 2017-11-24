package com.hermod.bottonline.fps.services.kafka;

import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaWithHeadersTemplate<K, V> extends KafkaTemplate {

    public KafkaWithHeadersTemplate(ProducerFactory producerFactory) {
        super(producerFactory);
    }

    public ListenableFuture<SendResult<K, V>> send(String topic, V data, String key, String replyTo, String BLEnvironment, String paymentType) {
        ProducerRecord<K, V> producerRecord = new ProducerRecord(topic, key, data);

        producerRecord.headers()
                .add(KafkaHeaders.REPLY_TO.getKafkaHeader(), replyTo.getBytes())
                .add(KafkaHeaders.FPS_SITE.getKafkaHeader(), BLEnvironment.getBytes())
                .add(KafkaHeaders.FPS_PAYMENT_TYPE.getKafkaHeader(), paymentType.getBytes());

        return this.doSend(producerRecord);
    }

    public ListenableFuture<SendResult<K, V>> sendRawMessage(String topic, V data, String key) {
        ProducerRecord<K, V> producerRecord = new ProducerRecord(topic, key, data);
        return this.doSend(producerRecord);
    }
}
