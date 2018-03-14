package com.orwellg.hermod.bottomline.fps.listeners.inbound.standin;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import static com.codahale.metrics.MetricRegistry.name;

@Component(value = "mqSTANDINSite2Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSTANDINSite2Listener extends MQSTANDINListener {

    @Value("${jms.mq.bottomline.environment.2}")
    private String environmentMQ;

    public static Logger LOG = LogManager.getLogger(MQSTANDINSite2Listener.class);

    public MQSTANDINSite2Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    protected String getEnvironment(){
        return environmentMQ;
    }
}
