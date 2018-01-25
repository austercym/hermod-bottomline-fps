package com.orwellg.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.ServiceStatusMessageType;
import com.orwellg.hermod.bottomline.fps.services.transform.ServiceStatusMessageTransform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;

public class ConvertUSMServiceStatusMessageTest {

	private final ServiceStatusMessageTransform _transform = new ServiceStatusMessageTransform();


	@Test
	public void conversionToAvroAndBackWorksForServiceStatusMesage() throws Exception {

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

		final File xmlFile = new File("src/test/resources/usm_servicestatusmessage.xml");

		ServiceStatusMessageType serviceStatusMessage = JAXB.unmarshal(xmlFile, ServiceStatusMessageType.class);
		return serviceStatusMessage;

	}
}
