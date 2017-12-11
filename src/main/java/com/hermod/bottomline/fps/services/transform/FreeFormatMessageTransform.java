package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_FreeFormatMessage")
public class FreeFormatMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.FreeFormatMessageType.class,
				com.orwellg.umbrella.avro.types.usm.FreeFormatMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.FreeFormatMessageType.class,
					com.bottomline.directfps.fpsusmelements.FreeFormatMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.FreeFormatMessageType)) {
			throw new ClassCastException("Expected FreeFormatMessage of type " + com.bottomline.directfps.fpsusmelements.FreeFormatMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.FreeFormatMessageType source =
				(com.bottomline.directfps.fpsusmelements.FreeFormatMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.FreeFormatMessageType target =
				new com.orwellg.umbrella.avro.types.usm.FreeFormatMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.FreeFormatMessageType)) {
			throw new ConversionException("Expected FreeFormatMessage of type " + com.orwellg.umbrella.avro.types.usm.FreeFormatMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.FreeFormatMessageType target = new com.bottomline.directfps.fpsusmelements.FreeFormatMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
