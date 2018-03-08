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
import javax.jms.MessageListener;

import static com.codahale.metrics.MetricRegistry.name;

@Component(value = "mqASYNListener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQASYNListener extends MQListener {

    public static String PAYMENT_TYPE = "ASYN";
    public static Logger LOG = LogManager.getLogger(MQASYNListener.class);



    @Override
    public void onMessage(Message message) {
        super.onMessage(message, PAYMENT_TYPE);
    }

    public MQASYNListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            inbound_sop_requests = metricRegistry.counter(name("connector_fps", "inbound", "sop", "requests", "count"));
            inbound_cbp_requests= metricRegistry.counter(name("connector_fps", "inbound", "cbp", "requests", "count"));
            inbound_fdp_requests = metricRegistry.counter(name("connector_fps", "inbound", "fdp", "requests", "count"));
            inbound_srn_requests = metricRegistry.counter(name("connector_fps", "inbound", "srn", "requests", "count"));
            inbound_rtn_requests = metricRegistry.counter(name("connector_fps", "inbound", "rtn", "requests", "count"));
           // final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
           // reporterJMX.start();
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ){
        calculateMetrics(paymentType);
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                replyTo, environmentMQ, paymentType, false
        );
    }

}
