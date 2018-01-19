package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_STLPaymentCodeMessageType")
public class STLPaymentCodeMessageTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType.class,
				com.orwellg.umbrella.avro.types.usm.STLPaymentCodeMessageType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.STLPaymentCodeMessageType.class,
					com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType)) {
			throw new ClassCastException("Expected STLPaymentCodeMessage of type " + com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType source =
				(com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType)message;
		
		final com.orwellg.umbrella.avro.types.usm.STLPaymentCodeMessageType target =
				new com.orwellg.umbrella.avro.types.usm.STLPaymentCodeMessageType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.STLPaymentCodeMessageType)) {
			throw new ConversionException("Expected STLPaymentCodeMessage of type " + com.orwellg.umbrella.avro.types.usm.STLPaymentCodeMessageType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType target = new com.bottomline.directfps.fpsusmelements.STLPaymentCodeMessageType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
