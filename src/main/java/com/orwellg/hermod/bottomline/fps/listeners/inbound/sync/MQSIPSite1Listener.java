package com.orwellg.hermod.bottomline.fps.listeners.inbound.sync;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(value = "mqSIPSite1Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSIPSite1Listener extends MQSIPListener {

    private static Logger LOG = LogManager.getLogger(MQSIPSite1Listener.class);

    @Value("${jms.mq.bottomline.environment.1}")
    private String environmentMQ;

    public MQSIPSite1Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    protected String getEnvironment(){

        return environmentMQ;
    }

}
