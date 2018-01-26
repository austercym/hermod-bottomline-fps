package com.orwellg.hermod.bottomline.fps.listeners.outbound;

import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component(value = "mqSIPOutboundRecvListener")
@Scope("prototype")
public class MQSIPOutboundRecvListener extends MQOutboundListener {

    public static final String PAYMENT_TYPE = "SIP";
    private static Logger LOG = LogManager.getLogger(MQSIPOutboundRecvListener.class);

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
                null, environmentMQ, paymentType, false
        );
    }
}
