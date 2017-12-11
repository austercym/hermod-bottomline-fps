package com.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.SchemeReturnPaymentFailureType;
import com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType;
import com.hermod.bottomline.fps.services.transform.SchemeReturnPaymentFailureTransform;
import com.hermod.bottomline.fps.services.transform.ServiceStatusMessageTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;

public class ConvertUSMSchemeReturnPaymentFailureTest {

	private final SchemeReturnPaymentFailureTransform _transform = new SchemeReturnPaymentFailureTransform();


	@Test
	public void conversionToAvroAndBackWorksForSchemeReturnPaymentFailure() throws Exception {

		FPSMessage message = readMessage();
		FPSAvroMessage result = null;
		try {
			result = _transform.fps2avro(message);
			assert(result != null);

			FPSMessage revert = _transform.avro2fps(result);
			assert(revert != null);
		}
		catch (Exception err) {
			err.printStackTrace();
			throw err;
		}		
	}
	

	private final static FPSMessage readMessage() throws Exception {

		final File xmlFile = new File("src/test/resources/usm_SchemeReturnPaymentFailure.xml");

		SchemeReturnPaymentFailureType schemeReturnPaymentFailure = JAXB.unmarshal(xmlFile, SchemeReturnPaymentFailureType.class);
		return schemeReturnPaymentFailure;

	}
}
