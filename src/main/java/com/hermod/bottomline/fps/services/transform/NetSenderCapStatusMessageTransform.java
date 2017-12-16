package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_NetSenderCapStatusMessageType")
public class NetSenderCapStatusMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType.class,
				com.orwellg.umbrella.avro.types.usm.NetSenderCapStatusMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.NetSenderCapStatusMessageType.class,
					com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType)) {
			throw new ClassCastException("Expected NetSenderCapStatusMessage of type " + com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType source =
				(com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.NetSenderCapStatusMessageType target =
				new com.orwellg.umbrella.avro.types.usm.NetSenderCapStatusMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.NetSenderCapStatusMessageType)) {
			throw new ConversionException("Expected NetSenderCapStatusMessage of type " + com.orwellg.umbrella.avro.types.usm.NetSenderCapStatusMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType target = new com.bottomline.directfps.fpsusmelements.NetSenderCapStatusMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
