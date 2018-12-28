package com.orwellg.hermod.bottomline.fps.listeners.inbound.asyn;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQListener;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;

public abstract class MQASYNListener extends MQListener {

    public static String PAYMENT_TYPE = "ASYN";
    public static Logger LOG = LogManager.getLogger(MQASYNListener.class);

    protected abstract String getEnvironment();

    public MQASYNListener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, PAYMENT_TYPE);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ, Long qosMilliseconds, boolean isReversal){
        calculateMetrics(paymentType, isReversal);
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false, false, qosMilliseconds
        );
    }

}
