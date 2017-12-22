package com.hermod.bottomline.fps.listeners.outbound;

import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component(value = "mqAsynOutboundRecvListener")
@Scope("prototype")
public class MQAsynOutboundRecvListener extends MQOutboundListener {

    public static final String PAYMENT_TYPE = "ASYN";
    private static Logger LOG = LogManager.getLogger(MQAsynOutboundRecvListener.class);

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
                null, bottomlineEnv, paymentType, false
        );

    }

}
