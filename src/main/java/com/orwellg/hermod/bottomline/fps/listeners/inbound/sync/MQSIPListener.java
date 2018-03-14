package com.orwellg.hermod.bottomline.fps.listeners.inbound.sync;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQListener;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;

import static com.codahale.metrics.MetricRegistry.name;

public abstract class  MQSIPListener extends MQListener {

    private static Logger LOG = LogManager.getLogger(MQSIPListener.class);

    public MQSIPListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            inbound_sip_requests = metricRegistry.counter(name("connector_fps", "inbound", "SIP", FPSDirection.INPUT.getDirection()));
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, SIP);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ){
        inbound_sip_requests.inc();
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false
        );

    }

}
