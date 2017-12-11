package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_ASPMAlertMessage")
public class ASPMAlertMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType.class,
				com.orwellg.umbrella.avro.types.usm.ASPMAlertMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.ASPMAlertMessageType.class,
					com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType)) {
			throw new ClassCastException("Expected ASPMAlertMessage of type " + com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType source =
				(com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.ASPMAlertMessageType target =
				new com.orwellg.umbrella.avro.types.usm.ASPMAlertMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.ASPMAlertMessageType)) {
			throw new ConversionException("Expected ASPMAlertMessage of type " + com.orwellg.umbrella.avro.types.usm.ASPMAlertMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType target = new com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
