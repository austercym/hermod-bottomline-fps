package com.hermod.bottomline.fps.utils.generators;

import com.orwellg.yggdrasil.net.client.producer.CommandProducerConfig;
import com.orwellg.yggdrasil.net.client.producer.GeneratorIdCommandProducer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.protocol.SecurityProtocol;
import org.apache.kafka.common.utils.Time;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component(value = "idGenerator")
public class IDGeneratorBean {

    public GeneratorIdCommandProducer generatorID(){
        Properties props  = new Properties();
        props.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.PLAINTEXT.name());
        props.setProperty(CommandProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hdf-node1:2181,hdf-node2:2181,hdf-node3:2181");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new GeneratorIdCommandProducer(new CommandProducerConfig(props), 1, Time.SYSTEM);
    }
}
