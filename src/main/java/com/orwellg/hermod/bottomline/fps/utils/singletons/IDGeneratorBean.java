package com.orwellg.hermod.bottomline.fps.utils.singletons;

import com.orwellg.hermod.bottomline.fps.spring.core.env.SpringArchaiusPropertySource;
import com.orwellg.yggdrasil.net.client.producer.CommandProducerConfig;
import com.orwellg.yggdrasil.net.client.producer.GeneratorIdCommandProducer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.protocol.SecurityProtocol;
import org.apache.kafka.common.utils.Time;

import java.util.Properties;

public class IDGeneratorBean {

    private static IDGeneratorBean instance = null;
    private GeneratorIdCommandProducer generatorIDProducer;

    //private constructor to avoid client applications to use constructor
    private IDGeneratorBean(){
        generatorIDProducer = new GeneratorIdCommandProducer(new CommandProducerConfig(getProperties()), 1, Time.SYSTEM);
    }

    // Lazy Initialization (If required then only)
    public static IDGeneratorBean getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (IDGeneratorBean.class) {
                if (instance == null) {
                    instance = new IDGeneratorBean();
                }
            }
        }
        return instance;
    }


    public GeneratorIdCommandProducer generatorID(){
        return generatorIDProducer;
    }

    private Properties getProperties() {
        Properties props  = new Properties();
        props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.PLAINTEXT.name());
        props.setProperty(CommandProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SpringArchaiusPropertySource.getZookeeperHost());
        props.setProperty(CommandProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty(CommandProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
