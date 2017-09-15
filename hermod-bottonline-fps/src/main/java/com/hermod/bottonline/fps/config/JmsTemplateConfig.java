package com.hermod.bottonline.fps.config;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.hermod.bottonline.fps.listeners.JmsListener;
import com.hermod.bottonline.fps.utils.factory.ConfigurationFactory;

@Configuration
@EnableJms
public class JmsTemplateConfig extends ComponentConfig {
	
	@Autowired
	private JmsListener mqListener;
	
	@Bean
	public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setClassesToBeBound(
	    		iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_004_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_007_001.Document.class,
	    		iso.std.iso._20022.tech.xsd.pacs_009_001.Document.class
	    	);
	    return marshaller;
	}
	
	@Bean
	public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) {
		
		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
	    jmsTemplate.setReceiveTimeout(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getReceiveTimeout());
	    
	    // Creating and asing the message converter for jmsTemplate
	    MarshallingMessageConverter converter = new MarshallingMessageConverter();
	    	converter.setMarshaller(marshaller());
	    	converter.setUnmarshaller(marshaller());
	    	// set this converter on the implicit Spring JMS template
	    	jmsTemplate.setMessageConverter(converter);
	    	    
	    return jmsTemplate;
	}
	
	@Bean
    public DefaultMessageListenerContainer jmsListenerContainer(ConnectionFactory connectionFactory)
    {
        DefaultMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setDestinationName(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getInboundQueue());
        listenerContainer.setMessageListener(mqListener);
        listenerContainer.setMaxConcurrentConsumers(ConfigurationFactory.getConfigurationParams().getMQConfigurationParams().getNumMaxConsumers());
        listenerContainer.setSessionTransacted(true);
        return listenerContainer;
    }
}
