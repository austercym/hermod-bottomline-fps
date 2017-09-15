package com.hermod.bottonline.fps.services.transform;

import com.hermod.bottonline.fps.types.FPSMessage;

public interface FPSTransform {

	public Object fps2avro(FPSMessage message);
	
	public FPSMessage avro2fps(Object message);
}
