package com.hermod.bottonline.fps.services.transform;

import org.springframework.stereotype.Component;

import com.hermod.bottonline.fps.types.FPSMessage;

@Component(value="transform_pacs_004_001")
public class Pasc004Transform implements FPSTransform {

	@Override
	public Object fps2avro(FPSMessage message) {
		return null;
	}

	@Override
	public FPSMessage avro2fps(Object message) {
		return null;
	}
}
