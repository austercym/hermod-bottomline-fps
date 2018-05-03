package com.orwellg.hermod.bottomline.fps.listeners.inbound.standin;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQListener;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;

import static com.codahale.metrics.MetricRegistry.name;

public abstract class MQSTANDINListener extends MQListener {

    public static Logger LOG = LogManager.getLogger(MQSTANDINListener.class);

    public MQSTANDINListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            inbound_standin_requests = metricRegistry.counter(name("connector_fps", "inbound", "STANDIN", FPSDirection.INPUT.getDirection()));
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, STANDIN);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ, Long qosMilliseconds){
        inbound_standin_requests.inc();
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false, true, qosMilliseconds
        );
    }

    @Override
    protected Event getRawMessageEvent(String message, String uuid, String eventName) {
        Event event = super.getRawMessageEvent(message, uuid, FPSEvents.FPS_HERMOD_BL_STANDIN_RECEIVED.getEventName());
        return event;
    }
}
