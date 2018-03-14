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

@Component(value = "mqSTANDINSite1Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQSTANDINSite1Listener extends MQSTANDINListener {

    @Value("${jms.mq.bottomline.environment.1}")
    private String environmentMQ;

    public static Logger LOG = LogManager.getLogger(MQSTANDINSite1Listener.class);

    public MQSTANDINSite1Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

    @Override
    protected String getEnvironment(){
        return environmentMQ;
    }
}
