package com.orwellg.hermod.bottomline.fps.listeners.inbound;

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

@Component(value = "mqSTANDINListener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSTANDINListener extends MQListener {

    public static String PAYMENT_TYPE = "STANDIN";
    public static Logger LOG = LogManager.getLogger(MQSTANDINListener.class);
    private Counter inbound_standin_requests;

    public MQSTANDINListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            inbound_standin_requests = metricRegistry.counter(name("fps_connector", "inbound", "standin", "requests", "count"));
          //  final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
           // reporterJMX.start();
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {
        inbound_standin_requests.inc();
        super.onMessage(message, PAYMENT_TYPE);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ){
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false, true
        );
    }
}
