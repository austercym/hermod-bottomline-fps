package com.orwellg.hermod.bottomline.fps.listeners.outbound;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jms.Message;

import static com.codahale.metrics.MetricRegistry.name;

@Component(value = "mqAsynOutboundRecvListener")
@Scope("prototype")
public class MQAsynOutboundRecvListener extends MQOutboundListener {

    public static final String PAYMENT_TYPE = "ASYN";
    private static Logger LOG = LogManager.getLogger(MQAsynOutboundRecvListener.class);

    public MQAsynOutboundRecvListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            responses = metricRegistry.timer(name("fps_connector", "inbound", "responses", "count"));
            responses_failures = metricRegistry.timer(name("fps_connector", "inbound", "responses", "failures", "count"));
            final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
            reporterJMX.start();
        }else{
            LOG.error("No existe metrics registry");
        }
    }

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
