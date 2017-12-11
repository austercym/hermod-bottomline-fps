package com.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.SITLChangeMessageType;
import com.bottomline.directfps.fpsusmelements.SettlementStatusMessageType;
import com.hermod.bottomline.fps.services.transform.SITLChangeMessageTransform;
import com.hermod.bottomline.fps.services.transform.SettlementStatusMessageTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;

public class ConvertUSMSITLChangeMessageTest {

	private final SITLChangeMessageTransform _transform = new SITLChangeMessageTransform();


	@Test
	public void conversionToAvroAndBackWorksForSITLChangeMessage() throws Exception {

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

		final File xmlFile = new File("src/test/resources/usm_SITLChangeMessage.xml");

		SITLChangeMessageType sITLChangeMessageType = JAXB.unmarshal(xmlFile, SITLChangeMessageType.class);
		return sITLChangeMessageType;

	}
}
