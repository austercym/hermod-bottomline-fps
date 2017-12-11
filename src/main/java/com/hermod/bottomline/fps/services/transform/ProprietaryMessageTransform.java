package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_ProprietaryMessage")
public class ProprietaryMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.ProprietaryMessageType.class,
				com.orwellg.umbrella.avro.types.usm.ProprietaryMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.ProprietaryMessageType.class,
					com.bottomline.directfps.fpsusmelements.ProprietaryMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.ProprietaryMessageType)) {
			throw new ClassCastException("Expected ProprietaryMessage of type " + com.bottomline.directfps.fpsusmelements.ProprietaryMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.ProprietaryMessageType source =
				(com.bottomline.directfps.fpsusmelements.ProprietaryMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.ProprietaryMessageType target =
				new com.orwellg.umbrella.avro.types.usm.ProprietaryMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.ProprietaryMessageType)) {
			throw new ConversionException("Expected ProprietaryMessage of type " + com.orwellg.umbrella.avro.types.usm.ProprietaryMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.ProprietaryMessageType target = new com.bottomline.directfps.fpsusmelements.ProprietaryMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
