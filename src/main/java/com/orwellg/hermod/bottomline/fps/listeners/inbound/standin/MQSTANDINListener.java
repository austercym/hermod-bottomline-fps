package com.orwellg.hermod.bottomline.fps.listeners.inbound.standin;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQListener;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;

import java.util.SortedMap;

import static com.codahale.metrics.MetricRegistry.name;

public abstract class MQSTANDINListener extends MQListener {

    public static Logger LOG = LogManager.getLogger(MQSTANDINListener.class);

    public MQSTANDINListener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, STANDIN);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ, Long qosMilliseconds, boolean isReversal){
        incrementStandinMetric();
        calculateMetrics(paymentType, isReversal);
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false, true, qosMilliseconds
        );
    }

    private void incrementStandinMetric() {
        SortedMap <String, Counter> counters = metricRegistry.getCounters();
        Counter inboundPooMetric = counters.get(name("connector_fps", "inbound", "STANDIN", FPSDirection.INPUT.getDirection()));
        if(inboundPooMetric!= null) {
            inboundPooMetric.inc();
        }
    }

    @Override
    protected Event getRawMessageEvent(String message, String uuid, String eventName) {
        Event event = super.getRawMessageEvent(message, uuid, FPSEvents.FPS_HERMOD_BL_STANDIN_RECEIVED.getEventName());
        return event;
    }
}
