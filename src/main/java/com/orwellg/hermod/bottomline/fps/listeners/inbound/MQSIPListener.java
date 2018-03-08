package com.orwellg.hermod.bottomline.fps.listeners.inbound;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
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

@Component(value = "mqSIPListener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSIPListener extends MQListener {

    private static Logger LOG = LogManager.getLogger(MQSIPListener.class);

    public MQSIPListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            inbound_sip_requests = metricRegistry.counter(name("connector_fps", "inbound", "SIP", FPSDirection.INPUT.getDirection()));
           // final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
            //reporterJMX.start();
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
