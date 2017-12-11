package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_NetSenderThresholdStatusChangeMessage")
public class NetSenderThresholdStatusChangeMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType.class,
				com.orwellg.umbrella.avro.types.usm.NetSenderThresholdStatusChangeMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.NetSenderThresholdStatusChangeMessageType.class,
					com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType)) {
			throw new ClassCastException("Expected NetSenderThresholdStatusChangeMessage of type " + com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType source =
				(com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.NetSenderThresholdStatusChangeMessageType target =
				new com.orwellg.umbrella.avro.types.usm.NetSenderThresholdStatusChangeMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.NetSenderThresholdStatusChangeMessageType)) {
			throw new ConversionException("Expected NetSenderThresholdStatusChangeMessage of type " + com.orwellg.umbrella.avro.types.usm.NetSenderThresholdStatusChangeMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType target = new com.bottomline.directfps.fpsusmelements.NetSenderThresholdStatusChangeMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
