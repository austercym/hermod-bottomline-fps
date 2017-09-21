package com.hermod.bottonline.fps.services.transform;

import org.springframework.stereotype.Component;

import com.hermod.bottonline.fps.services.transform.helper.ConversionException;
import com.hermod.bottonline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

@Component(value="transform_pacs_008_001")
public class Pasc008Transform implements FPSTransform {

	static {
		TransformationHelper.registerMapping(
			iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class, 
			com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class);						
	}

	
	@Override
	public FPSAvroMessage fps2avro(FPSMessage message) throws ConversionException {
		if (message == null) {
			return null;
		}
		
		if (!(message instanceof iso.std.iso._20022.tech.xsd.pacs_008_001.Document)) {
			throw new ClassCastException("Expected Document of type " + iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class.getTypeName());
		}
		
		final iso.std.iso._20022.tech.xsd.pacs_008_001.Document source = 
				(iso.std.iso._20022.tech.xsd.pacs_008_001.Document)message;
		
		final com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document target = 
				new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document();
		
		TransformationHelper.updateTargetValues(source, target);
		
		FPSAvroMessage avroMessage = new FPSAvroMessage(target);
				
		return avroMessage;
	}

	@Override
	public FPSMessage avro2fps(FPSAvroMessage message) {
		return null;
	}
}
