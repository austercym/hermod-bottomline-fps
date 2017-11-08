package com.hermod.bottonline.fps.listeners;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.kafka.listener.KafkaDataListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hermod.bottonline.fps.services.transform.FPSTransform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.event.Event;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.commons.types.utils.avro.RawMessageUtils;

@Component(value="kafkaListener")
public class KafkaListener extends BaseListener implements MessageListener<String, String>, KafkaDataListener<ConsumerRecord<String, String>> {

	@Value("${wq.mq.queue.outbound}")
	private String outboundQueue;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private JmsOperations jmsOperations;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> message) {
		
		try {
			// Get the event
			Event event = RawMessageUtils.decodeFromString(Event.SCHEMA$, message.value());
			
			// Get the correct value for the transform map
			FPSAvroMessage data = gson.fromJson(event.getEvent().getData(), FPSAvroMessage.class);
			
			// Call the correspondent transform
			FPSTransform transform = getTransform(data.getClass().getPackage().getName());
			if (transform != null) {			
	    			FPSMessage fpsMessage = transform.avro2fps(data);
	    			jmsOperations.convertAndSend(outboundQueue, fpsMessage);
			} else {
				throw new MessageConversionException("Exception in message emision. The transform for the class " + data.getClass().getName() + " is null");
			}
		 } catch (Exception e) {
     		throw new MessageConversionException("Exception in message emision. Message: " + e.getMessage(), e);
		 }
	}
	
}