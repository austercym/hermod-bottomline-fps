package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import org.springframework.stereotype.Component;

import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

@Component(value="transform_pacs_004_001")
public class Pasc004Transform implements FPSTransform {

	static {
		try {					
			TransformationHelper.registerMapping(
				iso.std.iso._20022.tech.xsd.pacs_004_001.Document.class, 
				com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs004_001_05.Document.class);						
	
			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs004_001_05.Document.class,
					iso.std.iso._20022.tech.xsd.pacs_004_001.Document.class);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	@Override
	public FPSAvroMessage fps2avro(FPSMessage message) throws ConversionException {
		if (message == null) {
			return null;
		}
		
		if (!(message instanceof iso.std.iso._20022.tech.xsd.pacs_004_001.Document)) {
			throw new ConversionException("Expected Document of type " + iso.std.iso._20022.tech.xsd.pacs_004_001.Document.class.getTypeName() + " but got " + message.getClass().getName() + " instead");
		}
		
		final iso.std.iso._20022.tech.xsd.pacs_004_001.Document source = 
				(iso.std.iso._20022.tech.xsd.pacs_004_001.Document)message;
		
		final com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs004_001_05.Document target = 
				new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs004_001_05.Document();
		
		TransformationHelper.updateTargetValues(source, target);
		
		FPSAvroMessage avroMessage = new FPSAvroMessage(target);
				
		return avroMessage;
	}

	@Override
	public FPSMessage avro2fps(FPSAvroMessage message) throws ConversionException {
		if (message == null) {
			return null;
		}
		
		final Object avroMessage = message.getMessage();
		if (avroMessage == null) {
			return null;
		}
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs004_001_05.Document)) {
			throw new ConversionException("Expected Document of type " + com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs004_001_05.Document.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final iso.std.iso._20022.tech.xsd.pacs_004_001.Document target = new iso.std.iso._20022.tech.xsd.pacs_004_001.Document();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;
	}
}
