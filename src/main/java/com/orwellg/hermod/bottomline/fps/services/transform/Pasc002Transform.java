package com.orwellg.hermod.bottomline.fps.services.transform;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.orwellg.hermod.bottomline.fps.services.transform.pacs002.Pacs002Avro2FPSTransform;
import com.orwellg.hermod.bottomline.fps.services.transform.pacs002.Pacs002FPS2AvroTransform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component(value="transform_pacs_002_001")
public class Pasc002Transform implements FPSTransform {

	private static Logger LOG = LogManager.getLogger(Pasc002Transform.class);

	static {
		try {
			TransformationHelper.registerMapping(
				iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class, 
				com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document.class);						
	
			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document.class,
					iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class);
		}
		catch (ConfigurationException err) {
			err.printStackTrace();
		}
	}
	
	@Override
	public FPSAvroMessage fps2avro(FPSMessage message) throws ConversionException {
		
		if (message == null) {
			return null;
		}
		
		if (!(message instanceof iso.std.iso._20022.tech.xsd.pacs_002_001.Document)) {
			throw new ClassCastException("Expected Document of type " + iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class.getTypeName());
		}
		
		final iso.std.iso._20022.tech.xsd.pacs_002_001.Document source = 
				(iso.std.iso._20022.tech.xsd.pacs_002_001.Document)message;
		
		final com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document target = 
				new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document();

		final com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document target2 =
				new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document();

		long startTransformation = new Date().getTime();
		Pacs002FPS2AvroTransform.transform(source, target);
		//TransformationHelper.updateTargetValues(source, target2);
		LOG.debug("[FPS] Transform from FPS to avro last {} ms ", new Date().getTime()-startTransformation);

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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document)) {
			throw new ConversionException("Expected Document of type " + com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final iso.std.iso._20022.tech.xsd.pacs_002_001.Document target = new iso.std.iso._20022.tech.xsd.pacs_002_001.Document();
		long startTransformation = new Date().getTime();
		Pacs002Avro2FPSTransform.transform((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs002_001_06.Document)avroMessage, target);
		//TransformationHelper.updateTargetValues(avroMessage, target);
		LOG.debug("[FPS] Transform from avro to FPS last {} ms ", new Date().getTime()-startTransformation);
		return target;	}

}
