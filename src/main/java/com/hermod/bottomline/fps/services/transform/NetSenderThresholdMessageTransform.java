package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_NetSenderThresholdMessage")
public class NetSenderThresholdMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType.class,
				com.orwellg.umbrella.avro.types.usm.NetSenderThresholdMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.NetSenderThresholdMessageType.class,
					com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType)) {
			throw new ClassCastException("Expected NetSenderThresholdMessage of type " + com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType source =
				(com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.NetSenderThresholdMessageType target =
				new com.orwellg.umbrella.avro.types.usm.NetSenderThresholdMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.NetSenderThresholdMessageType)) {
			throw new ConversionException("Expected NetSenderThresholdMessage of type " + com.orwellg.umbrella.avro.types.usm.NetSenderThresholdMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType target = new com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
