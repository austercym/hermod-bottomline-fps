package com.orwellg.hermod.bottomline.fps.listeners.outbound;

import com.codahale.metrics.MetricRegistry;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.fps.FPSDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.jms.Message;

import static com.codahale.metrics.MetricRegistry.name;

@Component(value = "mqSIPOutboundRecvListener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSIPOutboundRecvListener extends MQOutboundListener {

    private static Logger LOG = LogManager.getLogger(MQSIPOutboundRecvListener.class);

    public MQSIPOutboundRecvListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            outbound_sip_responses = metricRegistry.counter(name("connector_fps", "outbound", "SIP", FPSDirection.INPUT.getDirection()));
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
        outbound_sip_responses.inc();
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                null, environmentMQ, paymentType, false
        );
    }
}
