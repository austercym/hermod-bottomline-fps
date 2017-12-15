package com.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.ASPMAlertMessageType;
import com.hermod.bottomline.fps.services.transform.ASPMAlertMessageTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;

public class ConvertUSMASPMAlertMessageTest {

	private final ASPMAlertMessageTransform _transform = new ASPMAlertMessageTransform();


	@Test
	public void conversionToAvroAndBackWorksForASPMAlertMessage() throws Exception {

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

		final File xmlFile = new File("src/test/resources/usm_ASPMAlertMessage.xml");

		ASPMAlertMessageType serviceStatusMessage = JAXB.unmarshal(xmlFile, ASPMAlertMessageType.class);
		return serviceStatusMessage;

	}
}
