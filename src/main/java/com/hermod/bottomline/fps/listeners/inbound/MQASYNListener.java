package com.hermod.bottomline.fps.listeners.inbound;

import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component(value = "mqASYNListener")
@Scope("prototype")
public class MQASYNListener extends MQListener {

    public static String PAYMENT_TYPE = "ASYN";
    public static Logger LOG = LogManager.getLogger(MQASYNListener.class);

    @Value("${jms.mq.bottomline.environment.1}")
    private String bottomlineEnv;

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, PAYMENT_TYPE);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType){
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, bottomlineEnv, paymentType
        );

    }

}
