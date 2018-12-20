package com.orwellg.hermod.bottomline.fps.listeners.inbound.asyn;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.hermod.bottomline.fps.listeners.inbound.MQListener;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jms.Message;

import static com.codahale.metrics.MetricRegistry.name;

public abstract class MQASYNListener extends MQListener {

    public static String PAYMENT_TYPE = "ASYN";
    public static Logger LOG = LogManager.getLogger(MQASYNListener.class);

    protected abstract String getEnvironment();

    public MQASYNListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            String direction = FPSDirection.INPUT.getDirection();
            inbound_sop_requests = metricRegistry.counter(name("connector_fps", "inbound", "SOP", direction));
            inbound_cbp_requests = metricRegistry.counter(name("connector_fps", "inbound", "CBP", direction));
            inbound_fdp_requests = metricRegistry.counter(name("connector_fps", "inbound", "FDP", direction));
            inbound_srn_requests = metricRegistry.counter(name("connector_fps", "inbound", "SRN", direction));
            inbound_rtn_requests = metricRegistry.counter(name("connector_fps", "inbound", "RTN", direction));
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, PAYMENT_TYPE);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ, Long qosMilliseconds){
        calculateMetrics(paymentType);
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false, false, qosMilliseconds
        );
    }

}
