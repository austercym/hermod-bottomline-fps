package com.orwellg.hermod.bottomline.fps.services.transform;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.orwellg.hermod.bottomline.fps.services.transform.pacs008.Pacs008Avro2FPSTransform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

@Component(value="transform_pacs_008_001")
public class Pasc008Transform implements FPSTransform {

	private static Logger LOG = LogManager.getLogger(Pasc008Transform.class);
	static {
		try {
		    // Avro to XML
			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class,
					iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class);
            // XML to Avro
			TransformationHelper.registerMapping(
				iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class, 
				com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class);						
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
		
		if (!(message instanceof iso.std.iso._20022.tech.xsd.pacs_008_001.Document)) {
			throw new ConversionException("Expected Document of type " + iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class.getTypeName() + " but got " + message.getClass().getName() + " instead");
		}
		
		final iso.std.iso._20022.tech.xsd.pacs_008_001.Document source = 
				(iso.std.iso._20022.tech.xsd.pacs_008_001.Document)message;
		
		final com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document target = 
				new com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document();
        long startTransformation = new Date().getTime();
		TransformationHelper.updateTargetValues(source, target);
        LOG.debug("[FPS] Transform from FPS to Avro lasts {} ms ", new Date().getTime()-startTransformation);
        startTransformation = new Date().getTime();
		FPSAvroMessage avroMessage = new FPSAvroMessage(target);
        LOG.debug("[FPS] Transform from FPS to Avro - creating avroMessage lasts {} ms ", new Date().getTime()-startTransformation);
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

		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document)) {
			throw new ConversionException("Expected Document of type " + com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}

		final iso.std.iso._20022.tech.xsd.pacs_008_001.Document target = new iso.std.iso._20022.tech.xsd.pacs_008_001.Document();
		final iso.std.iso._20022.tech.xsd.pacs_008_001.Document target2 = new iso.std.iso._20022.tech.xsd.pacs_008_001.Document();

		long startTransformation = new Date().getTime();
		Pacs008Avro2FPSTransform.transform((com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document)avroMessage, target);
        //TransformationHelper.updateTargetValues(avroMessage, target2);
        LOG.debug("[FPS] Transform from avro to FPS last {} ms ", new Date().getTime()-startTransformation);


        return target;

	}


}
