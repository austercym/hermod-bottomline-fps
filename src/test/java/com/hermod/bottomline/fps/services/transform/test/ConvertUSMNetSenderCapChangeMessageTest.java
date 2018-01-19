package com.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.NetSenderCapChangeMessageType;
import com.hermod.bottomline.fps.services.transform.NetSenderCapChangeMessageTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;

public class ConvertUSMNetSenderCapChangeMessageTest {

	private final NetSenderCapChangeMessageTransform _transform = new NetSenderCapChangeMessageTransform();


	@Test
	public void conversionToAvroAndBackWorksForNetSenderCapChangeMessage() throws Exception {

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

		final File xmlFile = new File("src/test/resources/usm_NetSenderCapChangeMessage.xml");

		NetSenderCapChangeMessageType netSenderCapChangeMessage = JAXB.unmarshal(xmlFile, NetSenderCapChangeMessageType.class);
		return netSenderCapChangeMessage;

	}
}
