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

@Component(value = "mqAsynOutboundRecvListener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQAsynOutboundRecvListener extends MQOutboundListener {

    public static final String PAYMENT_TYPE = "ASYN";
    private static Logger LOG = LogManager.getLogger(MQAsynOutboundRecvListener.class);

    public MQAsynOutboundRecvListener(MetricRegistry metricRegistry){
        if(metricRegistry!= null) {
            outbound_sop_responses = metricRegistry.counter(name("connector_fps", "outbound", "sop", "responses", "count"));
            outbound_cbp_responses = metricRegistry.counter(name("connector_fps", "outbound", "cbp", "responses", "count"));
            outbound_fdp_responses = metricRegistry.counter(name("connector_fps", "outbound", "fdp", "responses", "count"));
            outbound_rtn_responses = metricRegistry.counter(name("connector_fps", "outbound", "rtn", "responses", "count"));
            outbound_srn_responses = metricRegistry.counter(name("connector_fps", "outbound", "srn", "responses", "count"));
          //  final JmxReporter reporterJMX = JmxReporter.forRegistry(metricRegistry).build();
          //  reporterJMX.start();
        }else{
            LOG.error("No exists metrics registry");
        }
    }

    @Override
    public void onMessage(Message message) {
        super.onMessage(message, PAYMENT_TYPE);
    }

    @Override
    protected void sendToKafka(String topic, String uuid, Event event, String paymentType, String environmentMQ){
        LOG.debug("[FPS][Payment type {}] Asyn", paymentType);
        calculateMetrics(paymentType);
        kafkaSender.send(
                topic,
                RawMessageUtils.encodeToString(Event.SCHEMA$, event),
                uuid,
                null, environmentMQ, paymentType, false
        );
    }

}
