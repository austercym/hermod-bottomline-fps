package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_ServiceStatusMessageType")
public class ServiceStatusMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType.class,
				com.orwellg.umbrella.avro.types.usm.ServiceStatusMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.ServiceStatusMessageType.class,
					com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType)) {
			throw new ClassCastException("Expected ServiceStatusMessage of type " + com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType source =
				(com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.ServiceStatusMessageType target =
				new com.orwellg.umbrella.avro.types.usm.ServiceStatusMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.ServiceStatusMessageType)) {
			throw new ConversionException("Expected ServiceStatusMessageType of type " + com.orwellg.umbrella.avro.types.usm.ServiceStatusMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType target = new com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
