package com.orwellg.hermod.bottomline.fps.listeners.inbound.sync;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(value = "mqSIPSite2Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSIPSite2Listener extends MQSIPListener {

    @Value("${jms.mq.bottomline.environment.2}")
    private String environmentMQ;

    private static Logger LOG = LogManager.getLogger(MQSIPSite2Listener.class);

    public MQSIPSite2Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    protected String getEnvironment(){

        return environmentMQ;
    }

}
