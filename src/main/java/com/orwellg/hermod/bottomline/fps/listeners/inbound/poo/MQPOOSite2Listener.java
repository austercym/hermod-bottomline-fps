package com.orwellg.hermod.bottomline.fps.listeners.inbound.poo;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(value = "mqPOOSite2Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQPOOSite2Listener extends MQPOOListener {

    @Value("${jms.mq.bottomline.environment.2}")
    private String environmentMQ;

    public static Logger LOG = LogManager.getLogger(MQPOOSite2Listener.class);

    @Override
    protected String getEnvironment(){
        return environmentMQ;
    }

    public MQPOOSite2Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }
}
