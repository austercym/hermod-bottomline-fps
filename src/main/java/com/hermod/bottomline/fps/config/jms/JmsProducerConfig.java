package com.hermod.bottomline.fps.config.jms;

import com.hermod.bottomline.fps.config.ComponentConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MarshallingMessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJms
public class JmsProducerConfig extends ComponentConfig {
	
	@Value("${wq.mq.receive.timeout}")
	private Long receiveTimeout;
	
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
	    jmsTemplate.setReceiveTimeout(receiveTimeout);
	    
	    // Creating and assing the message converter for jmsTemplate
	    MarshallingMessageConverter converter = new MarshallingMessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setMarshaller(marshaller());
		converter.setUnmarshaller(marshaller());
		// set this converter on the implicit Spring JMS template
		jmsTemplate.setMessageConverter(converter);
		jmsTemplate.setMessageIdEnabled(true);
		jmsTemplate.setMessageTimestampEnabled(true);

	    return jmsTemplate;
	}

	@Bean
	public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
		return jmsTransactionManager;
	}
	
}
