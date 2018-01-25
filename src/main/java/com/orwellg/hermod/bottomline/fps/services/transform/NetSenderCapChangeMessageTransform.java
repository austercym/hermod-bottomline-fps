package com.orwellg.hermod.bottomline.fps.services.transform;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_NetSenderCapChangeMessageType")
public class NetSenderCapChangeMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType.class,
				com.orwellg.umbrella.avro.types.usm.NetSenderCapChangeMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.NetSenderCapChangeMessageType.class,
					com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType)) {
			throw new ClassCastException("Expected NetSenderCapChangeMessage of type " + com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType source =
				(com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.NetSenderCapChangeMessageType target =
				new com.orwellg.umbrella.avro.types.usm.NetSenderCapChangeMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.NetSenderCapChangeMessageType)) {
			throw new ConversionException("Expected NetSenderCapChangeMessage of type " + com.orwellg.umbrella.avro.types.usm.NetSenderCapChangeMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType target = new com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
