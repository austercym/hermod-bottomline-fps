package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_SITLChangeMessage")
public class SITLChangeMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.SITLChangeMessageType.class,
				com.orwellg.umbrella.avro.types.usm.SITLChangeMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.SITLChangeMessageType.class,
					com.bottomline.directfps.fpsusmelements.SITLChangeMessageType.class
					);

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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.SITLChangeMessageType)) {
			throw new ClassCastException("Expected SITLChangeMessage of type " + com.bottomline.directfps.fpsusmelements.SITLChangeMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.SITLChangeMessageType source =
				(com.bottomline.directfps.fpsusmelements.SITLChangeMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.SITLChangeMessageType target =
				new com.orwellg.umbrella.avro.types.usm.SITLChangeMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.SITLChangeMessageType)) {
			throw new ConversionException("Expected SITLChangeMessage of type " + com.orwellg.umbrella.avro.types.usm.SITLChangeMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.SITLChangeMessageType target = new com.bottomline.directfps.fpsusmelements.SITLChangeMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
