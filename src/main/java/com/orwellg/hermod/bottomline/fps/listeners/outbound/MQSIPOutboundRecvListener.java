package com.orwellg.hermod.bottomline.fps.listeners.outbound;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
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

    public static final String PAYMENT_TYPE = "SIP";
    private static Logger LOG = LogManager.getLogger(MQSIPOutboundRecvListener.class);

    private Counter outbound_sync_responses;

    public MQSIPOutboundRecvListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            outbound_sync_responses = metricRegistry.counter(name("fps_connector", "outbound", "sync", "responses", "count"));
         //   final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
          //  reporterJMX.start();
        }else{
            LOG.error("No existe metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {
        outbound_sync_responses.inc();
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
