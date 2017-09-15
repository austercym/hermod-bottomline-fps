package com.hermod.bottonline.fps.services.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hermod.bottonline.fps.types.FPSMessage;

@Component
public class MQServiceImpl implements JmsService {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Override
	@Transactional(value = "jmsTransactionManager")
	public <T extends FPSMessage> void send(String destination, T message) {
		jmsTemplate.convertAndSend(destination, message);
	}

	@Override
	public FPSMessage receive(String source) {
		Object message = jmsTemplate.receiveAndConvert(source);
		if (message instanceof FPSMessage) { return (FPSMessage) message; }
		return null;
	}

}
