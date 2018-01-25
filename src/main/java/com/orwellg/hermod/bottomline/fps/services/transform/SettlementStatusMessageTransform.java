package com.orwellg.hermod.bottomline.fps.services.transform;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_SettlementStatusMessageType")
public class SettlementStatusMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType.class,
				com.orwellg.umbrella.avro.types.usm.SettlementStatusMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.SettlementStatusMessageType.class,
					com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType)) {
			throw new ClassCastException("Expected SettlementStatusMessage of type " + com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType source =
				(com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.SettlementStatusMessageType target =
				new com.orwellg.umbrella.avro.types.usm.SettlementStatusMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.SettlementStatusMessageType)) {
			throw new ConversionException("Expected SettlementStatusMessage of type " + com.orwellg.umbrella.avro.types.usm.SettlementStatusMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType target = new com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
