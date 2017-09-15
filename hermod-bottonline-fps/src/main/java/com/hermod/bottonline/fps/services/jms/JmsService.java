package com.hermod.bottonline.fps.services.jms;

import com.hermod.bottonline.fps.types.FPSMessage;

public interface JmsService {

	public <T extends FPSMessage> void send(String destination, T message);
	
	public FPSMessage receive(String source);
}
