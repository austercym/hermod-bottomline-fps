package com.orwellg.hermod.bottomline.fps.listeners.inbound.asyn;

import com.codahale.metrics.MetricRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component(value = "mqASYNSite2Listener")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MQASYNSite2Listener extends MQASYNListener {

    @Value("${jms.mq.bottomline.environment.2}")
    private String environmentMQSite2;

    public static Logger LOG = LogManager.getLogger(MQASYNSite2Listener.class);

    @Override
    protected String getEnvironment(){
        LOG.info("[FPS] Environment for Site 2  is {}", environmentMQSite2);
        return environmentMQSite2;
    }

    public MQASYNSite2Listener(MetricRegistry metricRegistry){
        super(metricRegistry);
    }

}
