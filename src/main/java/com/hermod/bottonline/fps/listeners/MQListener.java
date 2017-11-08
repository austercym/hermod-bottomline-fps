package com.hermod.bottonline.fps.listeners;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hermod.bottonline.fps.services.kafka.KafkaSender;
import com.hermod.bottonline.fps.services.transform.FPSTransform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.hermod.bottonline.fps.utils.generators.EventGenerator;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;
import com.orwellg.umbrella.commons.utils.enums.FPSEvents;

@Component(value="mqListener")
public class MQListener extends BaseListener implements MessageListener {

	private static Logger LOG = LogManager.getLogger(MQListener.class);
    
	@Autowired
	private Gson gson;
	
	@Value("{entity.name}")
	private String entity;
	@Value("${brand.name}")
	private String brand;
	@Value("${kafka.topic.outbound}")
	private String outboundTopic;
	
	@Autowired
	private Jaxb2Marshaller marshaller;
	
	@Autowired
	private KafkaSender kafkaSender;
	
    @Override
    public void onMessage(Message message) {
    	
        LOG.info("Entered in messagge reception ...............");
        InputStream stream = null; 
        Reader reader = null;
        Source source = null;
        
        try {
	        if (message instanceof TextMessage) {
	            	reader = new StringReader(((TextMessage) message).getText());
	        } else if (message instanceof BytesMessage) {
            		BytesMessage msg = (BytesMessage) message;
            		byte[] data = new byte[(int) msg.getBodyLength()];
                msg.readBytes(data);
                stream = new ByteArrayInputStream(data);
                
                reader = new InputStreamReader(stream);
	        } else {
	        		throw new MessageConversionException("The received message with type " + message.getJMSType() + " is not recognized.");
	        }
	        
	        if (reader != null) {
	        		source = new StreamSource(reader);
	        		FPSMessage fpsMessage = (FPSMessage) marshaller.unmarshal(source);
	        		
	        		// Call the correspondent transform
	        		FPSTransform transform = getTransform(fpsMessage.getClass().getPackage().getName());
	        		if (transform != null) {
		        		Object avroFpsMessage = transform.fps2avro(fpsMessage);
		        		
		        		// Send avro message to Kafka
		        		Event event = EventGenerator.generateEvent(
		        				this.getClass().getName(), FPSEvents.FPS_REQUEST_RECEIVED.getEventName(), 
		        				gson.toJson(avroFpsMessage), 
		        				entity, 
		        				brand
		        			);
		        		kafkaSender.send(
		        				outboundTopic, 
		        				RawMessageUtils.encodeToString(Event.SCHEMA$, event)
		        			);
	        		} else {
	        			throw new MessageConversionException("Exception in message reception. The transform for the class " + fpsMessage.getClass().getName() + " is null");
	        		}
	        }
        } catch (Exception e) {
        		throw new MessageConversionException("Exception in message reception. Message: " + e.getMessage(), e);
        } finally {
        		try {
	        		if (reader != null) { reader.close(); }
	    			if (stream != null) { stream.close(); }
        		} catch (Exception e) {
        			LOG.error("Error when try close the streams resources. Message: {}", e.getMessage(), e);
        		}
        }
    }
}
