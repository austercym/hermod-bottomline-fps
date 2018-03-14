package com.orwellg.hermod.bottomline.fps.listeners.inbound.asyn;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(value = "mqASYNSite1Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQASYNSite1Listener extends MQASYNListener {

    @Value("${jms.mq.bottomline.environment.1}")
    private String environmentMQ;

    public static Logger LOG = LogManager.getLogger(MQASYNSite1Listener.class);

    @Override
    protected String getEnvironment(){
        LOG.info("[FPS] Environment for Site 1  is {}", environmentMQ);
        return environmentMQ;
    }

    public MQASYNSite1Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

}
