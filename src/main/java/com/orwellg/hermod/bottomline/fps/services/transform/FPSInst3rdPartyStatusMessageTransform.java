package com.orwellg.hermod.bottomline.fps.services.transform;

import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.orwellg.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_FPSInst3RdPartyStatusMessageType")
public class FPSInst3rdPartyStatusMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType.class,
				com.orwellg.umbrella.avro.types.usm.FPSInst3rdPartyStatusMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.FPSInst3rdPartyStatusMessageType.class,
					com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType)) {
			throw new ClassCastException("Expected FPSInst3RdPartyStatusMessage of type " + com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType source =
				(com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.FPSInst3rdPartyStatusMessageType target =
				new com.orwellg.umbrella.avro.types.usm.FPSInst3rdPartyStatusMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.FPSInst3rdPartyStatusMessageType)) {
			throw new ConversionException("Expected FPSInst3rdPartyStatusMessage of type " + com.orwellg.umbrella.avro.types.usm.FPSInst3rdPartyStatusMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType target = new com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
