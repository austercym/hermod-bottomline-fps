package com.hermod.bottomline.fps.services.transform;

import com.hermod.bottomline.fps.services.transform.helper.ConfigurationException;
import com.hermod.bottomline.fps.services.transform.helper.ConversionException;
import com.hermod.bottomline.fps.services.transform.helper.TransformationHelper;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.springframework.stereotype.Component;

@Component(value="transform_SchemeReturnPaymentFailureType")
public class SchemeReturnPaymentFailureTransform implements FPSTransform {

	static {
		try {
			TransformationHelper.registerMapping(
					com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType.class,
				com.orwellg.umbrella.avro.types.usm.SchemeReturnPaymentFailureType.class);

			TransformationHelper.registerMapping(
					com.orwellg.umbrella.avro.types.usm.SchemeReturnPaymentFailureType.class,
					com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType.class
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
		
		if (!(message instanceof com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType)) {
			throw new ClassCastException("Expected SchemeReturnPaymentFailure of type " + com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType.class.getTypeName());
		}
		
		final com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType source =
				(com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType)message;
		
		final com.orwellg.umbrella.avro.types.usm.SchemeReturnPaymentFailureType target =
				new com.orwellg.umbrella.avro.types.usm.SchemeReturnPaymentFailureType();
		
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
		
		if (!(avroMessage instanceof com.orwellg.umbrella.avro.types.usm.SchemeReturnPaymentFailureType)) {
			throw new ConversionException("Expected SchemeReturnPaymentFailure of type " + com.orwellg.umbrella.avro.types.usm.SchemeReturnPaymentFailureType.class.getTypeName() + " but got " + avroMessage.getClass().getName() + " instead");
		}
		
		final com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType target = new com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType();
		
		TransformationHelper.updateTargetValues(avroMessage, target);
		
		return target;	}

}
