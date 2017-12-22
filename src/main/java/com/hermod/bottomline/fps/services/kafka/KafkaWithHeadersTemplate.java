package com.hermod.bottomline.fps.services.kafka;

import com.orwellg.umbrella.commons.utils.enums.KafkaHeaders;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
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

    public ListenableFuture<SendResult<K, V>> send(String topic, V data, String key, String replyTo, String BLEnvironment,
                                                   String paymentType, boolean isPOO) {
        ProducerRecord<K, V> producerRecord = new ProducerRecord(topic, key, data);

        Headers headers = producerRecord.headers();
        if(StringUtils.isNotEmpty(replyTo)){
            headers.add(KafkaHeaders.REPLY_TO.getKafkaHeader(), replyTo.getBytes());
        }
        if(StringUtils.isNotEmpty(BLEnvironment)){
            headers.add(KafkaHeaders.FPS_SITE.getKafkaHeader(), BLEnvironment.getBytes());
        }
        if(StringUtils.isNotEmpty(paymentType)){
            headers.add(KafkaHeaders.FPS_PAYMENT_TYPE.getKafkaHeader(), paymentType.getBytes());
        }
        headers.add(KafkaHeaders.FPS_PAYMENT_POO.getKafkaHeader(), Boolean.toString(isPOO).getBytes());



        return this.doSend(producerRecord);
    }

    public ListenableFuture<SendResult<K, V>> sendRawMessage(String topic, V data, String key) {
        ProducerRecord<K, V> producerRecord = new ProducerRecord(topic, key, data);
        return this.doSend(producerRecord);
    }
}
