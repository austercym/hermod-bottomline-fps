package com.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.NetSenderThresholdMessageType;
import com.hermod.bottomline.fps.services.transform.NetSenderThresholdMessageTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;

public class ConvertUSMNetSenderThresholdMessageTest {

	private final NetSenderThresholdMessageTransform _transform = new NetSenderThresholdMessageTransform();


	@Test
	public void conversionToAvroAndBackWorksForNetSenderThresholdMessage() throws Exception {

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

		final File xmlFile = new File("src/test/resources/usm_NetSenderThresholdMessage.xml");

		NetSenderThresholdMessageType netSenderThresholdMessage = JAXB.unmarshal(xmlFile, NetSenderThresholdMessageType.class);
		return netSenderThresholdMessage;

	}
}
