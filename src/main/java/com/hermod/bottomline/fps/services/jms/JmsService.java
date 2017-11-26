package com.hermod.bottomline.fps.services.jms;

import com.hermod.bottomline.fps.types.FPSMessage;

public interface JmsService {

	public <T extends FPSMessage> void send(String destination, T message);
	
	public FPSMessage receive(String source);
}
