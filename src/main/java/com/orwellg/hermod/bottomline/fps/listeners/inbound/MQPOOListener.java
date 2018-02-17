package com.orwellg.hermod.bottomline.fps.listeners.inbound;

import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component(value = "mqPOOListener")
@Scope("prototype")
public class MQPOOListener extends MQListener {

    public static String PAYMENT_TYPE = "POO";
    public static Logger LOG = LogManager.getLogger(MQPOOListener.class);

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, PAYMENT_TYPE);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ){
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, PAYMENT_TYPE, true
        );
    }
}
