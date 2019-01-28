package com.orwellg.hermod.bottomline.fps.listeners.inbound.poo;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQListener;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;

import java.util.SortedMap;

import static com.codahale.metrics.MetricRegistry.name;

public abstract class MQPOOListener extends MQListener {

    public static Logger LOG = LogManager.getLogger(MQPOOListener.class);

    @Override
    protected abstract String getEnvironment();

    public MQPOOListener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, POO);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ, Long qosMilliseconds, boolean isReversal){

        calculateMetrics(paymentType, isReversal);
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, true,false, qosMilliseconds
        );
    }
    
}
