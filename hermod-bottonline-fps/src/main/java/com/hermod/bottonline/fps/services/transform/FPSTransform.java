package com.hermod.bottonline.fps.services.transform;

import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

public interface FPSTransform {

	public FPSAvroMessage fps2avro(FPSMessage message);
	
	public FPSMessage avro2fps(FPSAvroMessage message);
}
